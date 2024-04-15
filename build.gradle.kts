plugins {
    id("java")
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "2.5.3"
}

group = "org.manager"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Spring
dependencies {
    implementation("org.springframework.boot","spring-boot-starter-web")
    implementation("org.springframework.boot","spring-boot-starter-data-jpa")
}

// PostgreSQL
dependencies {
    implementation("org.postgresql","postgresql", "42.2.19")

}

// Lombok
dependencies {
    implementation("org.projectlombok:lombok")
}


tasks.test {
    useJUnitPlatform()
}

