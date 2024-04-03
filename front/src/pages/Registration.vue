<script>
export default {
  data() {
    return {
      login: '',
      email: '',
      password: ''
    };
  },
  methods: {
    submitForm() {
      console.log('Отправляем данные: (шутка, мы ниче не отправляем пока)', this.login, this.email, this.password);
      this.$router.push({path: "/login"})
    },
    logIn() {
      this.$router.push({path: "/login"})
    }
  }
};
</script>

<template>
  <div class="registration-form">
    <h2>РЕГИСТРАЦИЯ</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="login">Логин:</label>
        <input type="text" id="login" v-model="login" required>
      </div>
      <div class="form-group">
        <label for="email">Почта:</label>
        <input type="email" id="email" v-model="email" required>
      </div>
      <div class="form-group">
        <label for="password">Пароль:</label>
        <input type="password" id="password" v-model="password" required>
      </div>
      <div class="form-group">
        <label for="password">Повтор пароля:</label>
        <input type="password" id="password" v-model="password" required>
      </div>
      <v-btn
          class="submit-button"
          type="submit"
          color="primary"
          @click=""
      >
        Зарегестрироваться
      </v-btn>
      <div>
        <p class="text-but" @click="logIn">Войти</p>
      </div>
    </form>
  </div>
</template>

<style scoped>
.registration-form {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  min-width: 400px;
  max-width: 500px;
  min-height: 500px;
  max-height: 650px;
  margin-right: 35px;
  padding: 20px 20px 0;
  border-radius: 20px;
  border: 1px solid #C0CFD3;
  background: #F7FBFC;
}

.submit-button {
  border-radius: 30px;
  padding: 30px ;
  width: 200px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px auto;

}

.form-group {
  margin-bottom: 12px;
  width: 100%;
//background: #445365;
}

.button {

}

.text-but {
  text-align: center;
  cursor: pointer;
}

input {
  width: 100%;
  border: 1px solid #C0CFD3;
  border-radius: 20px;
  padding: 10px 10px 10px 20px;

}

form {
  margin-top: 20px
}

h2 {
  text-align: center;
}

label {
  display: block;
  margin-bottom: 5px;
}
</style>