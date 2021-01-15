package br.com.dca.usecases

import br.com.dca.domains.Pet
import br.com.dca.exceptions.ResourceNotFoundException
import br.com.dca.gateways.PetGateway
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class DeletePetSpec extends Specification {

    def petGateway = Mock(PetGateway);

    def deletePet = new DeletePet(petGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("br.com.dca.templates")
    }

    def "Excluir um pet cadastrado"() {
        given: "quero excluir o pet com o id 2"
        def idPet = 2

        when: "realizo a exclusão do pet pelo id"
        deletePet.execute(idPet)

        then: "é excluído o pet"
        1 * petGateway.delete(_)
        1 * petGateway.findById(_) >> { Optional.ofNullable(from(Pet).gimme("pet-type-cat")) }
    }

    def "Excluir um pet com um id inválido"() {
        given: "quero excluir o pet com o id 3"
        def idPet = 3

        when: "tento realizar a exclusão do pet pelo id"
        deletePet.execute(idPet)

        then: "uma exception de erro é retornada"
        1 * petGateway.findById(_) >> { Optional.empty() }
        thrown ResourceNotFoundException
    }

}
