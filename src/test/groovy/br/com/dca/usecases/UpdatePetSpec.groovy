package br.com.dca.usecases

import br.com.dca.domains.Pet
import br.com.dca.exceptions.ResourceNotFoundException
import br.com.dca.gateways.PetGateway
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class UpdatePetSpec extends Specification {

    def petGateway = Mock(PetGateway);

    def updatePet = new UpdatePet(petGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("br.com.dca.templates")
    }

    def "Alterar um pet cadastrado"() {
        given: "quero alterar um pet cadastrado"
        Pet pet = from(Pet).gimme("pet-type-dog")
        pet.setName("Alterado")

        when: "realizo a alteração do pet"
        def resultPet = updatePet.execute(pet)

        then: "é alterado os dados do pet"
        resultPet.name == "Alterado"
        1 * petGateway.save(_) >> { pet }
        1 * petGateway.findById(_) >> { Optional.ofNullable(from(Pet).gimme("pet-type-dog")) }
    }

    def "Alterar um pet que não existe"() {
        given: "quero alterar um pet inexistente"
        def pet = new Pet()

        when: "tento realizar a alteração do pet"
        updatePet.execute(pet)

        then: "uma exception de erro é retornada"
        1 * petGateway.findById(_) >> { Optional.empty() }
        thrown ResourceNotFoundException
    }

}
