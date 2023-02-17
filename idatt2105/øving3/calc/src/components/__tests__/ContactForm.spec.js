import { describe, it, expect } from 'vitest'

import ContactForm from '@/components/ContactForm.vue';
import { mount } from '@vue/test-utils';


describe('ContactView', () => {
  const wrapper = mount(ContactForm)

  // check if the component is mounted
  it('is mounted', () => {
    expect(wrapper.vm).toBeTruthy();
  })


  it('renders the form', () => {
    // find the submitForm
    expect(wrapper.find('.contactForm').exists()).toBe(true);
  })

  it('has input fields with empty strings', () => {
    expect(wrapper.vm.inputName).to.equal('');
    expect(wrapper.vm.inputEmail).to.equal('');
    expect(wrapper.vm.inputMessage).to.equal('');
  })

  it('errorMsg and status is null', () => {
    expect(wrapper.vm.errorMsg).toBe('');
    expect(wrapper.vm.status).toBe(null);
  })

  
 
  it('sets myStateVar to a new value', async () => {
    const name = 'Elon';

    await wrapper.vm.$nextTick(); 

    wrapper.setData({ inputName: name });

    expect(wrapper.vm.inputName).toBe(name);
  })

  it('sets statevariables to a new values', async () => {
    const name = 'Elon Musk';
    const email = 'elon@tesla.com';
    const message = 'im a rocket dude';

    wrapper.setData({ inputName: name });
    wrapper.setData({ inputEmail: email });
    wrapper.setData({ inputMessage: message });

    await wrapper.vm.$nextTick(); 

    expect(wrapper.vm.inputName).toBe(name);
    expect(wrapper.vm.inputEmail).toBe(email);
    expect(wrapper.vm.inputMessage).toBe(message);
  })

  it('disables button if any input field is empty', async () => {
    wrapper.vm.inputName = 'Elon';
    wrapper.vm.inputEmail = '';
    wrapper.vm.inputMessage = 'Bruh';
    await wrapper.vm.evalInput();
    expect(wrapper.vm.isButtonDisabled).to.be.true;
    expect(wrapper.vm.errorMsg).to.equal('An input is empty');
  })

  it('disables button if email is not in the correct format', async () => {
    wrapper.vm.inputName = 'Elon Musk';
    wrapper.vm.inputEmail = 'elon@tesla.com';
    wrapper.vm.inputMessage = 'im a rocket dude';
    await wrapper.vm.evalInput();
    expect(wrapper.vm.isButtonDisabled).to.be.true;
  })
})

