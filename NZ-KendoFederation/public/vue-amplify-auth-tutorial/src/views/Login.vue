<template>
    <div class="container">
    <form @submit.prevent="login">
        <h2>Login</h2>
        <input
            type="email"
            v-model="email"
            placeholder="Email address..."
        />
        <input
            type="password"
            v-model="password"
            placeholder="password..."
        />
        <button>Login</button>
    </form>
</div>
</template>

<script>
import { Auth } from 'aws-amplify';
export default {
  name: 'Login',
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
      async login() {
        try {
            await Auth.signIn(this.email, this.password);
            alert('Successfully logged in');
        }  catch (error) {
            alert(error.message);
        }
    },
  },
}
</script>