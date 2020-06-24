function toggleFormAdicionarItens() {
    var formAdicionarItens = document.getElementById("adicionar");

    if (formAdicionarItens.classList.contains("hide")) {
        document.getElementById("adicionar").style.display = "block";
        setTimeout(
            () => {
                document.getElementById("adicionar").classList.remove("hide");
            }, 100);
        document.getElementById("nav-adc").setAttribute("href", "/#adicionar");
        
    } else {
        formAdicionarItens.classList.add("hide");
        setTimeout( () => {
            document.getElementById("adicionar").style.display = "none";
        } ,500);

        document.getElementById("nav-adc").setAttribute("href", "/#");
    }
}

function toggleListarFontesCadastradas() {
    var btnListarFontes = document.getElementById("listar-fontes");
    var btnDeslistarFontes = document.getElementById("deslistar-fontes");
    var divFontes = document.getElementById("fontes");

    if (btnListarFontes.classList.contains("hide")) {
        btnListarFontes.style.display = "block";
        btnDeslistarFontes.style.display = "none";
        divFontes.style.display = "none";
        btnListarFontes.classList.remove("hide");
    } else {
        btnListarFontes.style.display = "none";
        btnDeslistarFontes.style.display = "block";
        divFontes.style.display = "block";
        btnListarFontes.classList.add("hide");
    }

}
//funcao auxiliar para retirar uma array de strings entre beg e end de uma string
function extract([beg, end]) {
    const matcher = new RegExp(`${beg}(.*?)${end}`,'gm');
    const normalise = (str) => str.slice(beg.length,end.length*-1);
    return function(str) {
        return str.match(matcher).map(normalise);
    }
}
function toggleBusca() {
    const div = document.getElementById('searching');
    var stringComplete = div.value.toLowerCase();
    if(stringComplete.includes('"')){
        const stringsComAspas = extract([`"`,`"`])(stringComplete);
        stringsComAspas.forEach(function(string1){
            stringComplete = stringComplete.replace('"' + string1 + '"', '')
            console.log(stringComplete)
        })
        var stringsSemAspas = stringComplete.trim().split(/\s+/);
        console.log(stringsSemAspas[0])

        if(stringsSemAspas[0] != ''){
            var todasStrings = stringsSemAspas.concat(stringsComAspas);
        }else{
            var todasStrings = stringsComAspas.slice()
        }
    }else{
        var todasStrings = stringComplete.trim().split(/\s+/);
    }
    const articles = document.getElementsByClassName('card border-dark mx-auto noticia');
    Array.from(articles).forEach(function(article){
        const firstdiv = article.firstElementChild;
        const title = firstdiv.firstElementChild.textContent.toLowerCase(); //pega o titulo
        const c = article.children;
        k = c[2].children;  //pega o elemento pai da descrição, o k[0] é o primeiro <p>, lugar a ser procurado  
        article.style.display = 'none';
        todasStrings.forEach(function(term){
            if(k[0].textContent.toLowerCase().includes(term) || title.includes(term) ){
                article.style.display = 'block';
            }
        })
        })
}
//enter para o input funcionar
function searchKeyPress(e)
{
    e = e || window.event;
    if (e.keyCode == 13)
    {
        document.getElementById('botaoSearch').click();
        return false;
    }
    return true;
}
//atualiza a página para deixar sempre as noticias mais recentes
setTimeout(function() { 
    window.location.reload(1);
  }, 180000);

//dinamicidade do botão filtrar por data
function toggleBuscarPorData(){
    const myDiv = document.getElementById("dinamicData");
    if (myDiv.style.display === "block") {
        myDiv.style.display = "none";
      } else {
        myDiv.style.display = "block";
      }
}

//Tirando o fuso das data pra exibir mais bonito
const dates = document.getElementsByClassName('data');
    Array.from(dates).forEach(function(date){
        let array= date.textContent.split('+').join('-').split('-');
        date.innerHTML = array[0];

    })