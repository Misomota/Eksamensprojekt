# Eksamensprojekt - On The Dot

**On The Dot** 
Dette projekt er et projektkalkulationsværktøj udviklet til **Alpha Solutions**.
Systemet gør det muligt at oprette, nedbryde og estimere arbejdstid for projekter. Formålet er at give brugerer bedre overblik over tid, ressourcer og deadlines.

![On The Dot](resources/static/images/logo.PNG)

---

## Teknologier

### Backend
- Java 21
- Spring Boot 3.5.7
- Spring Web
- JDBC Template
- H2 til integrationstest
- MySQL Azure Database
- Qodana
Frontend
•  HTML. 
•  CSS.
• Thymeleaf.

## Forudsætninger og installation
For at køre projektet lokalt, skal du have de korrekte versioner af ovenstående teknologier installeret.

### Klon repository
Kør følgende kommando i terminalen:
```bash
git clone https://github.com/Misomota/Eksamensprojekt.git
cs Eksamensprojekt
```

### Lokalt konfiguration
Indstil følgende miljøvariabler til lokal udvikling:

```text
spring.datasource.url=jdbc:mysql://localhost:3306/Eksamen
spring.datasource.username=<dit-brugernavn>
spring.datasource.password=<dit-kodeord>
```

### Deployment:
- CI/CD: gihub actions.
- Hosting: azure
- Database: azure
- Kørende applikation: [On The Dot] https://onthedot-g4h6dvcradgxh4ak.swedencentral-01.azurewebsites.net

Medlemmer af projekt:
Mona & Mie
