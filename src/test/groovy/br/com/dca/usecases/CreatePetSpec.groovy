package br.com.dca.usecases

import br.com.dca.domains.Pet
import br.com.dca.gateways.PetGateway
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class CreatePetSpec extends Specification {

    def petGateway = Mock(PetGateway);

    def createPet = new CreatePet(petGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("br.com.dca.templates")
    }

    def "Cadastrar um novo pet"() {
        given: "quero cadastrar um novo pet"
        Pet pet = from(Pet).gimme("pet-type-dog")

        when: "realizo o cadastro do novo pet"
        def resultPet = createPet.execute(pet)

        then: "é criado o novo pet"
        resultPet.id == 1l
        resultPet.name == "Duque"
        1 * petGateway.save(_) >> { pet }
    }

}
