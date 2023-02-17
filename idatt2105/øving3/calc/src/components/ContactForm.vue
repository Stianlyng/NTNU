<script>
import CollectionService from '@/services/CollectionService'

  export default {
    data() {
      return {
        inputName: "",
        inputEmail: "",
        inputMessage: "",
        status: null,
        isButtonDisabled: true,
        errorMsg: ""
      }
    },
    methods: {
      submitForm () {

        this.$store.dispatch(
          'createMessage',        
          {
            name: this.inputName,
            email: this.inputEmail,
            message: this.inputMessage
          }
        )
        
        this.status = this.getStatus()
        this.resetFields()

      },
      async getStatus() {
        var message = ""
        try {
            let result = await CollectionService.getStatus();
            console.log(result.data.response)
            message = result.data.response
            this.status = message
            this.disableBtn()
        } catch (error) {
            console.error(error);
        }
      },

      resetMessage() {
        this.status = null;
      },

      evalInput() {  
        this.resetMessage()

        if(this.inputName === "" || this.inputEmail === "" || this.inputMessage === "") {
            this.disableBtn();
            this.errorMsg = "An input is empty"
        } 
        else if (!this.inputEmail.match(/^\S+@\S+\.\S+$/)) {
            this.disableBtn();
            this.errorMsg = "Incorrect e-mail format! ex: 'example@feks.no'"
        }
        else {
            this.handleName()
        }
      },

      handleName() {
        let isName = true;
            let nameStrings = this.inputName.split(" ", this.inputName.length)
            for(let i = 0; i < nameStrings.length; i++) {
                if(nameStrings[i][0] != " ") {
                    if(nameStrings[i][0] !== nameStrings[i][0].toUpperCase()){
                    isName = false;
                    break;
                    } 
                }
            }
            if(isName) {
                this.enableBtn();
                this.errorMsg = "";
            }
            else {
                this.disableBtn();
                this.errorMsg = "Name should start with a capitalized letter!"
            } 
      },

      disableBtn () {
        this.isButtonDisabled = true
      },
      enableBtn () {
        this.isButtonDisabled = false
      },
      resetFields() {
        this.inputName = ""
        this.inputEmail = ""
        this.inputMessage = ""
      }
    },

    mounted() {
      this.disableBtn()
    },

    watch: {
      inputName() {
        this.evalInput()
      },
      inputEmail() {
        this.evalInput()
      },
      inputMessage() {
        this.evalInput()
      },
    
    }
  }
</script>

<template>
    <div class="contactForm">
      <h1>Give feedback</h1>
        <form @submit.prevent="submitForm">         
            <input class="formInput" type="text" id="nameInput" name="name" v-model="inputName" placeholder="Name...">
            <input class="formInput" type="email" id="emailInput" name="email" v-model="inputEmail" placeholder="Email...">
            <textarea class="formInput" name="message" id="messageInput" v-model="inputMessage" placeholder="Message..."></textarea>
            <input type="submit" id="submitButton" value="Send" :disabled="isButtonDisabled">
        </form>
        <p v-if="errorMsg != ''" id="excpMessage">{{ errorMsg }}</p>
        <p id="statusP" v-if="status">{{ status }}</p>
    </div>
</template>

<style scoped>
   .contactForm {
    height: auto;
    width: 100%;
    margin: auto;
    background-color: rgba(255, 255, 255, 0.35);
    padding: 3rem 4rem;
    border: 2px solid var(--color-text);
    box-shadow: 7px 6px 0 0 var(--color-text);
    border-radius: var(--border-radius);
    max-width: 500px;
    transition: transform 0.35s;
   }

   .contactForm label {
      display: block;
      margin: 20px;
      font-size: 18px;
      font-weight: bold;
   }

   #nameInput, #emailInput, #messageInput {
      width: 100%;
      padding: 8px 8px;
      margin-top: 10px;
      box-sizing: border-box;
      border: 2px solid #000000;
      font-size: 16px;
      background-color: transparent;
   }
   #submitButton {
      color: black;
      padding: 12px 12px;
      margin-top: 10px;
      border: 1px solid #010101;
      font-size: 16px;
   }
   #submitButton:hover {
      color: var(--color-background);
      background-color: var(--color-text);
      font-weight: bold;
   }
   
   #excpMessage {
    color: rgb(6, 6, 6);
   }
</style>
