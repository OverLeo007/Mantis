<script>
import AuthApi from "@/api/auth/AuthApi.js";

export default {
  data() {
    return {
      login: '',
      email: '',
      password: '',
      password_repeat: ''
    };
  },
  methods: {
    async submitForm() {
      console.log('Отправляем данные регистрации: (шутка, мы ниче не отправляем пока)', this.login, this.email, this.password);
      try {
        await AuthApi.register(this.login, this.email, this.password);
      } catch (error) {
        console.log(error);
      }

      this.$router.push({path: "/login"});
    },
    logIn() {
      this.$router.push({path: "/login"});
    }
  }
};
</script>

<template>
  <div class="login-scr">
    <div class="logo-card">
      <p id="logo">ЛОГОТИП</p>
      <p class="text">Лишь стремящиеся вытеснить традиционное производство
        , нанотехнологии, которые представляют собой яркий пример
        континентально-европейского типа политической культуры, б
        удут ассоциативно распределены по отраслям. </p>
    </div>
    <div class="login-form">
    <h2>РЕГИСТРАЦИЯ</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <input placeholder="Логин" type="text" id="login" v-model="login" required>
      </div>
      <div class="form-group">
        <input placeholder="Почта" type="email" id="email" v-model="email" required>
      </div>
      <div class="form-group">
        <input placeholder="Пароль" type="password" id="password" v-model="password" required>
      </div>
      <div class="form-group">
        <input placeholder="Повтор пароля" type="password" id="password" v-model="password_repeat" required>
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
  </div>
</template>

<style scoped>
.login-scr {
  width: 50%;
  height: 500px;
  min-width: 600px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 50px;
  border: 1px solid #C0CFD3;
  background: #F7FBFC;
  overflow: hidden;
  border-spacing: 0;
  display: flex;
}

.logo-card {
  width: 50%;
  background-image: linear-gradient(rgba(16, 75, 119, 1), rgba(23, 67, 101, .3)), url("@/assets/bg.jpg");
  background-position: center;
  background-size: cover;
  vertical-align: top;
  justify-content: center;
  align-items: center;
  padding: 50px 40px;
  display: flex;
  flex-direction: column;
}

.login-form {
  width: 50%;
  padding: 30px 20px 20px;
}

.text {
  margin-top: 30px;
  text-align: center;
  color: #FFFFFF;
}

.submit-button {
  border-radius: 30px;
  padding: 30px;
  width: 200px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 40px auto 10px;
  background: #AFD7F4;
//background: linear-gradient(to right, #FFFFFF, #AFD7F4);
//color: #AFD7F4;
}

.form-group {
  margin: 10px 0 10px;
  width: 100%;
}

input {
  width: 100%;
  height: 50px;
  border: 1px solid #C0CFD3;
  border-radius: 50px;
  padding: 10px 10px 10px 20px;
}

#logo {
  font-size: 30px;
  color: #FFFFFF;
//font-family: "Arial", serif;

}

.text-but {
  text-align: center;
  cursor: pointer;
}

form {
  align-items: center;
  margin-top: 20px;
}

tr {
  overflow: hidden;
}

h2 {
  font-size: 28px;
  text-align: center;
  margin-bottom: 30px;
}

label {
  display: block;
  margin-bottom: 5px;
}
</style>