package br.com.dca.usecases

import br.com.dca.domains.Customer
import br.com.dca.exceptions.ResourceNotFoundException
import br.com.dca.gateways.CustomerGateway
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class FindCustomerSpec extends Specification {

    def customerGateway = Mock(CustomerGateway);

    def findCustomer = new FindCustomer(customerGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("br.com.dca.templates")
    }

    def "Buscar a lista de todos os clientes cadastros"() {
        given: "quero buscar todos os clientes cadastrados"

        when: "realizo a busca de todos os clientes cadastrados"
        def resultCustomers = findCustomer.all()

        then: "é retornado os clientes que estão cadastrados"
        assert resultCustomers.size() == 1
        assert resultCustomers[0].email == "teste@teste.com.br"
        1 * customerGateway.findAll() >> { from(Customer).gimme(1, "customer") }
    }

    def "Buscar um cliente pelo id e encontra-lo cadastrado"() {
        given: "quero buscar o customer com o id 1"
        def idCustomer = 1

        when: "realizo a busca do cliente pelo id"
        def resultCustomer = findCustomer.byId(idCustomer)

        then: "é retornado os dados do cliente"
        assert resultCustomer.email == "teste@teste.com.br"
        1 * customerGateway.findById(_) >> { Optional.ofNullable(from(Customer).gimme("customer")) }
    }

    def "Buscar um cliente por um id que não existe e lançará uma exception"() {
        given: "quero buscar o cliente com o id 3"
        def idCustomer = 3

        when: "realizo a busca do cliente pelo id"
        findCustomer.byId(idCustomer)

        then: "Uma exception é retornada porque o cliente não existe para o id informado "
        1 * customerGateway.findById(_) >> { Optional.empty() }
        thrown ResourceNotFoundException
    }
}
