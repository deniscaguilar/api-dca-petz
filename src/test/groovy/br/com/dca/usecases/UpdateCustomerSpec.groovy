package br.com.dca.usecases

import br.com.dca.domains.Customer
import br.com.dca.exceptions.ResourceNotFoundException
import br.com.dca.gateways.CustomerGateway
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class UpdateCustomerSpec extends Specification {

    def customerGateway = Mock(CustomerGateway);

    def updateCustomer = new UpdateCustomer(customerGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("br.com.dca.templates")
    }

    def "Alterar um cliente cadastrado"() {
        given: "quero alterar um cliente cadastrado"
        Customer customer = from(Customer).gimme("customer")
        customer.setName("Alterado")

        when: "realizo a alteração do cliente"
        def resultCustomer = updateCustomer.execute(customer)

        then: "é alterado os dados do cliente"
        assert resultCustomer.name == "Alterado"
        1 * customerGateway.save(_) >> { customer }
        1 * customerGateway.findById(_) >> { Optional.ofNullable(from(Customer).gimme("customer")) }
    }

    def "Alterar um cliente que não existe"() {
        given: "quero alterar um cliente inexistente"
        def customer = new Customer()

        when: "tento realizar a exclusão do cliente"
        updateCustomer.execute(customer)

        then: "uma exception de erro é retornada"
        1 * customerGateway.findById(_) >> { Optional.empty() }
        thrown ResourceNotFoundException
    }

}
