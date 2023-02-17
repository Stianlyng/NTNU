// https://docs.cypress.io/api/introduction/api.html
/// ESSENTIAL: https://docs.cypress.io/guides/core-concepts/introduction-to-cypress

describe('Testing the urls', () => {
  it('visits the app root url', () => {
    cy.visit('localhost:4173')
    cy.contains('h4', 'Calculator Rex')
  })

  it('visits the app contact url', () => {
    cy.visit('localhost:4173/contact')
    cy.contains('h1', 'Give feedback')
  })
})

describe('Testing that form cannot send due to various factors', () => {

  it('visits the contact view and inputs only name and email -> displays correct error', () => {
    cy.visit('localhost:4173/contact')
    cy.contains('h1', 'Give feedback')
    cy.get("#nameInput").type("Elon Musk"); 

    cy.get("#emailInput").type("elon@tesla.com"); 

    cy.get("#excpMessage") 
    .should("contain", "An input is empty");
  })

  it('visits the contact view and inputs email on the wrong format -> displays correct error', () => {
    cy.visit('localhost:4173/contact')
    cy.contains('h1', 'Give feedback')
    cy.get("#nameInput").type("Elon Musk"); 

    cy.get("#emailInput").type("elon@com"); 

    cy.get("#messageInput").type("Random message"); 

    cy.get("#excpMessage") 
    .should("contain", "Incorrect e-mail format! Include all components: 'username@domainname.extension'");
  })

  it('visits the contact view and inputs name on the wrong format -> displays correct error', () => {
    cy.visit('localhost:4173/contact')
    cy.contains('h1', 'Give feedback')
    cy.get("#nameInput").type("elon musk"); 

    cy.get("#emailInput").type("elon@tesla.com"); 

    cy.get("#messageInput").type("Random message"); 

    cy.get("#excpMessage") 
    .should("contain", "Each name component should start with a capitalized letter!");
  })

  it('visits the contact view and inputs only name and email -> button is disabled', () => {
    cy.visit('localhost:4173/contact')
    cy.contains('h1', 'Give feedback')
    cy.get("#nameInput").type("Elon Musk");

    cy.get("#emailInput").type("elon@tesla.com"); 
    cy.get("#submitButton") 
    .should('be.disabled')
  })
})

describe('Testing that form submits and displays success', () => {

  it('visits the contact view and inputs correct inputs -> displays success', () => {
    cy.visit('localhost:4173/contact')
    cy.contains('h1', 'Give feedback')
    cy.get("#nameInput").type("Elon Musk"); 

    cy.get("#emailInput").type("elon@tesla.com"); 

    cy.get("#messageInput").type("Random message"); 

    cy.get("#submitButton") 
    .should('be.enabled')

    cy.get("#submitButton") 
    .click()

    cy.get("#statusP").should("contain", "Success");
  })

})


