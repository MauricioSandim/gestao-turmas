package ufla.projeto_es.gestao_turmas.testeValidacao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class validacoes {


    private WebDriver driver;



    @BeforeEach
    void setup(){

        driver = new ChromeDriver();

        driver.get(
            "http://localhost:5173/criarConta"
        );
    }

    void login() {

        driver.get("http://localhost:5173/login");

        driver.findElement(By.id("email"))
                .sendKeys("a@gmail.com");

        driver.findElement(By.id("password"))
                .sendKeys("123456789");

        driver.findElement(By.id("Continua"))
                .click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Espera até existir um token no localStorage
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        System.out.println(alert.getText());

        alert.accept();

        wait.until(ExpectedConditions.urlContains("/"));

        String token = (String) ((JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem('token');");

        System.out.println("TOKEN = " + token);
    }


    @Test
    void senhaDeveTerOitoCaracteres(){


        driver.findElement(
            By.id("nome")
        )
        .sendKeys("Aluno Teste");



        driver.findElement(
            By.id("email")
        )
        .sendKeys(
            "aluno@email.com"
        );



        driver.findElement(
            By.id("password")
        )
        .sendKeys(
            "12345"
        );

        WebElement campoRole =
            driver.findElement(
                By.cssSelector("select.InputForm")
            );


        Select select =
            new Select(campoRole);


        select.selectByValue("PROFESSOR");

        driver.findElement(
            By.id("Continua")
        )
        .click();


        WebElement campoSenha =
            driver.findElement(
                By.id("password")
            );


        String mensagem =
            campoSenha.getAttribute(
                "validationMessage"
            );


        assertTrue(
            mensagem.length() > 0 && mensagem.contains("8")
        );

        
    }

    @Test
    void criarTurmaComSucesso() {

        login();


        // clica em Nova Turma

       WebDriverWait wait =
            new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
            );


        WebElement botao =
            wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Nova Turma')]")
                )
            );


        botao.click();


        // preenche nome

        WebElement campoNome =
            driver.findElement(
                By.cssSelector(
                    "input[placeholder='Nome da Turma']"
                )
            );


        String nomeTurma = "TurmaTeste_" + System.currentTimeMillis();
        campoNome.sendKeys(
            nomeTurma
        );


        // salva

        driver.findElement(
            By.xpath("//button[contains(text(),'Salvar')]")
        )
        .click();



        // espera atualizar a lista

        WebDriverWait wait2 =
            new WebDriverWait(
                driver,
                Duration.ofSeconds(5)
            );


        WebElement turma =
            wait2.until(
                ExpectedConditions
                .visibilityOfElementLocated(
                    By.xpath(
                    "//*[contains(text(), nomeTurma)]"
                    )
                )
            );



        assertTrue(
            turma.isDisplayed()
        );

    }

    @Test
    void naoDevePermitirCriarDuasTurmasComMesmoNome() throws InterruptedException {

        login();
       
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String nomeTurma = "TurmaTeste_" + System.currentTimeMillis();

        // cria a primeira
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Nova Turma')]")))
            .click();
        

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Nome da Turma']")))
            .sendKeys(nomeTurma);
        
        driver.findElement(
                By.xpath("//button[contains(text(),'Salvar')]"))
            .click();


        Thread.sleep(10);


        System.out.println(driver.getTitle());

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'" + nomeTurma + "')]")));
        System.out.println("6 - Primeira turma criada");


        // tenta criar novamente
        WebElement botao = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Nova Turma')]")
            )
        );

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();", botao);

        Thread.sleep(10);
  
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Nome da Turma']")));

        campo.clear();
        campo.sendKeys(nomeTurma);
        

        driver.findElement(
                By.xpath("//button[contains(text(),'Salvar')]"))
            .click();

            
        // Aguarda um instante para o backend responder
        Thread.sleep(10);  

        assertTrue(
            driver.findElement(
                By.xpath("//button[contains(text(),'Salvar')]")
            ).isDisplayed()
        );

    }

    @AfterEach
    void finalizar(){

        driver.quit();
    
    }

}
