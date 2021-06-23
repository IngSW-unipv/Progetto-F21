# Progetto-F21
***Sistema per la gestione delle prenotazioni di biglietti di un cinema.***

![image](https://user-images.githubusercontent.com/80333091/113708434-81895080-96e1-11eb-85db-60251d9deaf9.png)

*** 
## 0. CONFIGURE YOUR ENVIRONMENT

### 0.1 - Download Git
In caso non si abbia già installato Git, andare al seguente [link](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) ;  selezionare l'installazione a seconda del proprio sistema operativo. 

### 0.2 - Fork this project into your own GitHub repositories & clone it
- **0.2.1:** Effettuare il **fork** del progetto (tasto *Fork* in alto a destra).
- **0.2.2:** Dopo di che, **dal vostro repository locale** (fork di questo progetto), cliccare sul bottone *"Code"* e copiare il link qui riportato.
- **0.2.3:** Successivamente aprire il terminale e spostarsi in una cartella in cui fare il clone di questo progetto (ex: Temp/Progetto-F21)
- **0.2.4:** Eseguire infine il comando:

```
git clone [link copiato]
```

### 0.3 - Download Gradle
Per poter eseguire l'applicazione è necessario avere un'installazione locale di [Gradle](https://it.wikipedia.org/wiki/Gradle).

Di seguito è riportato un link contenente tutte le istruzioni per poter fare il setup di Gradle sul vostro dispositivo, qualora non lo aveste già precedentemente installato.
Trovate inoltre i procedimenti per l'installazione a seconda del sistema operativo che state utilizzando.

[Gradle installation](https://gradle.org/install/)


*** 
## 1. WEB APPLICATION
### 1.1 - Open terminal/command prompt

Aprite il vostro terminale/prompt dei comandi per poter eseguire i comandi che saranno riportati nei passaggi successivi.
Prima però spostatevi nella cartella in cui, precedentemente, avete fatto il clone, tramite il comando:

```
cd  [ example: Temp/Progetto-F21 ]
```

### 1.2 - Build application

```
gradle clean build
```

### 1.3 - Run application
Con dispositivi Windows:

```
gradlew run
```

Con dispositivi Linux/MacOS: 

```
./gradlew run
```

Qualora uno dei due comandi sopracitati dicesse che manchi la classe wrapper, esegui il seguente comando:

```
gradle wrapper
```

### 1.4 - Open your browser
Tramite un browser (preferibilmente Chrome) inserisci nella barra di ricerca questo link: 
[`http://localhost:8080/`](http://localhost:8080/)


*** 
## 2. CLI APPLICATION
Attraverso un IDE (che supporti Java), esegui la classe CLIMain.java, presente nel percorso qui sotto riportato:
- [src/main/java/cinema/view/cli/CLIMain.java](https://github.com/IngSW-unipv/Progetto-F21/blob/main/src/main/java/cinema/view/cli/CLIMain.java)
