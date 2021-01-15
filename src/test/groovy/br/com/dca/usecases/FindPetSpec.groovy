package br.com.dca.usecases

import br.com.dca.domains.Pet
import br.com.dca.domains.PetType
import br.com.dca.exceptions.ResourceNotFoundException
import br.com.dca.gateways.PetGateway
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class FindPetSpec extends Specification {

    def petGateway = Mock(PetGateway);

    def findPet = new FindPet(petGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("br.com.dca.templates")
    }

    def "Buscar a lista de todos os pets cadastros"() {
        given: "quero buscar todos os pets cadastrados"

        when: "realizo a busca de todos os pets cadastrados"
        def resultPets = findPet.all()

        then: "é retornado os pets que estão cadastrados"
        assert resultPets.size() == 1
        assert resultPets[0].type == PetType.DOG
        1 * petGateway.findAll() >> { from(Pet).gimme(1, "pet-type-dog") }
    }

    def "Buscar um pet pelo id e encontra-lo cadastrado"() {
        given: "quero buscar o pet com o id 2"
        def idPet = 2

        when: "realizo a busca do pet pelo id"
        def resultPet = findPet.byId(idPet)

        then: "é retornado os dados pet"
        assert resultPet.name == "Flor"
        assert resultPet.type == PetType.CAT
        1 * petGateway.findById(_) >> { Optional.ofNullable(from(Pet).gimme("pet-type-cat")) }
    }

    def "Buscar um pet por um id que não existe que lançará uma exception"() {
        given: "quero buscar o pet com o id 3"
        def idPet = 3

        when: "realizo a busca do pet pelo id"
        def resultPet = findPet.byId(idPet)

        then: "Uma exception é retornada porque o pet não existe para o id informado "
        1 * petGateway.findById(_) >> { Optional.empty() }
        thrown ResourceNotFoundException
    }

}
