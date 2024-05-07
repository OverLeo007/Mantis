<script>
import AuthApi from "@/api/auth/AuthApi.js";

import {useVuelidate} from '@vuelidate/core'
import {required, minLength, email, sameAs, maxLength} from '@vuelidate/validators'
import * as $v from "@vuelidate/validators";
import {reactive} from "vue";

export default {
  data() {
    return {
      v$: useVuelidate(),
      login: '',
      email: '',
      password: '',
      password_repeat: ''
    };
  },

  validations() {
    return {
      login: {required, minLength: minLength(3), maxLengthValue: maxLength(30)},
      email: {required, email},
      password: {required, minLength: minLength(6), maxLengthValue: maxLength(30)},
      password_repeat: {required, sameAsPassword: sameAs(this.password)}
    }
  },

  methods: {
    async submitForm() {
      this.v$.$validate()
      console.log(this.v$)
      if (this.v$.$error) {
        return
      }
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
      <p class="text"> Мы рады приветствовать вас в нашем инструменте управления задачами!</p>
      <p class="text">Здесь вы сможете эффективно организовывать свою работу, отслеживать процесс выполнения
        задач и сотрудничать с коллегами! Управляйте проектами грамотно с Mantis!</p>
    </div>
    <div class="login-form">
      <h2>РЕГИСТРАЦИЯ</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <input placeholder="Логин" type="text" id="login" v-model="login">
          <p class="err-message" v-if="v$.login.$error">! {{ v$.login.$errors[0].$message }}</p>
        </div>
        <div class="form-group">
          <input placeholder="Почта" type="text" id="email" v-model="email">
          <p class="err-message" v-if="v$.email.$error">! {{ v$.email.$errors[0].$message }}</p>
        </div>
        <div class="form-group">
          <input placeholder="Пароль" type="text" id="password" v-model="password">
          <p class="err-message" v-if="v$.password.$error">! {{ v$.password.$errors[0].$message }}</p>
        </div>
        <div class="form-group">
          <input placeholder="Повтор пароля" type="text" id="password_repeat" v-model="password_repeat">
          <p class="err-message" v-if="v$.password_repeat.$error">! {{ v$.password_repeat.$errors[0].$message }}</p>
        </div>
        <div id="but">
          <v-btn
              class="submit-button"
              type="submit"
          >
            Зарегестрироваться
          </v-btn>
          <div>
            <p class="text-but" @click="logIn">Войти</p>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.login-scr {
  width: 50%;
  min-height: 520px;
  max-height: 600px;
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
  background-image: linear-gradient(rgba(16, 75, 119, 1), rgba(23, 67, 101, .4)), url("@/assets/bg.jpg");
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
  position: relative;
}

.text {
  text-align: center;
  color: #FFFFFF;
  font-size: 14px;
  margin-bottom: 10px;
}

#but {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  width: 100%;
  bottom: 10px;


}

.submit-button {
  border-radius: 30px;
  padding: 30px;
  width: 200px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 10px auto 10px;
  background: #AFD7F4;
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
  margin-bottom: 30px;
//font-family: "Arial", serif;

}

.text-but {
  text-align: center;
  cursor: pointer;
  margin-bottom: 10px;
  margin-top: 10px;
}

form {
  align-items: center;
  margin-top: 20px;
}

.err-message {
  margin-right: 20px;
  margin-left: 20px;
  font-size: 10px;
  color: red;
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