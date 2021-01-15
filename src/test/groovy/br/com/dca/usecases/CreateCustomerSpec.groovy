package br.com.dca.usecases

import br.com.dca.domains.Customer
import br.com.dca.exceptions.CustomerAlreadyExistsException
import br.com.dca.gateways.CustomerGateway
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class CreateCustomerSpec extends Specification {

    def customerGateway = Mock(CustomerGateway);

    def createCustomer = new CreateCustomer(customerGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("br.com.dca.templates")
    }

    def "Cadastrar um novo cliente"() {
        given: "quero cadastrar um novo cliente"
        Customer customer = from(Customer).gimme("customer")

        when: "realizo o cadastro do novo cliente"
        def resultCustomer = createCustomer.execute(customer)

        then: "é criado o novo cliente"
        assert resultCustomer.id == 1l
        assert resultCustomer.email == "teste@teste.com.br"
        1 * customerGateway.save(_) >> { customer }
        1 * customerGateway.findByEmail(_) >> { Optional.empty() }
    }

    def "Tentar cadastrar um novo cliente cujo email já existe"() {
        given: "quero cadastrar um novo cliente"
        Customer customer = from(Customer).gimme("customer")

        when: "tento realizar o cadastro do novo cliente"
        def resultCustomer = createCustomer.execute(customer)

        then: "um erro é retornado porque o email do cliente já existe no cadastro"
        thrown CustomerAlreadyExistsException
        0 * customerGateway.save(_) >> { customer }
        1 * customerGateway.findByEmail(_) >> { Optional.ofNullable(from(Customer).gimme("customer")) }
    }

}
