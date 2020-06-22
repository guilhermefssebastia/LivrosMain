package com.example.livrosmain;

public class Api{

        private static final String ROOT_URL = "http://192.168.1.63/LivroApi/v1/Api.php?apicall=";
        public static final String URL_CREATE_CADASTRO_CLI = ROOT_URL + "createCadastroCli";
        public static final String URL_GET_CADASTRO_CLI = ROOT_URL + "getCadastroCli";
        public static final String URL_UPDATE_CLI = ROOT_URL + "updatecli";
        public static final String URL_DELETE_CLI = ROOT_URL + "deletecli&codCli";
        public static final  String URL_CREATE_CADASTRO_LIVRO = ROOT_URL + "createCadastroLivro";
        public static final  String URL_GET_CADASTRO_LIVRO = ROOT_URL + "getCadastroLivro";
        public static final  String URL_UPDATE_CADASTRO_LIVRO = ROOT_URL + "updateCadastroLivro";
        public static final  String URL_DELETE_CADASTRO_LIVRO = ROOT_URL + "deleteCadastroLivros";



    }