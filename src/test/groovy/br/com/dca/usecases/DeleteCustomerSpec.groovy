package br.com.dca.usecases

import br.com.dca.domains.Customer
import br.com.dca.exceptions.ResourceNotFoundException
import br.com.dca.gateways.CustomerGateway
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class DeleteCustomerSpec extends Specification {

    def customerGateway = Mock(CustomerGateway);

    def deleteCustomer = new DeleteCustomer(customerGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("br.com.dca.templates")
    }

    def "Excluir um cliente cadastrado"() {
        given: "quero excluir o cliente com o id 1"
        def idCustomer = 1

        when: "realizo a exclusão do cliente pelo id"
        deleteCustomer.execute(idCustomer)

        then: "é excluído o cliente"
        1 * customerGateway.delete(_)
        1 * customerGateway.findById(_) >> { Optional.ofNullable(from(Customer).gimme("customer")) }
    }

    def "Excluir um cliente com um id inválido"() {
        given: "quero excluir o cliente com o id 3"
        def idCustomer = 3

        when: "tento realizar a exclusão do cliente pelo id"
        deleteCustomer.execute(idCustomer)

        then: "uma exception de erro é retornada"
        1 * customerGateway.findById(_) >> { Optional.empty() }
        thrown ResourceNotFoundException
    }

}
