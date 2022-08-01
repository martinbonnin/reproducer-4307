plugins {
  id("org.jetbrains.kotlin.jvm").version("1.7.10")
  id("com.apollographql.apollo3").version("3.4.0")
}

repositories {
  mavenCentral()
}
dependencies {
  implementation("com.apollographql.apollo3:apollo-runtime:3.4.0")
  testImplementation("junit:junit:4.13.2")
  testImplementation("com.apollographql.apollo3:apollo-mockserver:3.4.0")
}

apollo {
  useVersion2Compat()
}