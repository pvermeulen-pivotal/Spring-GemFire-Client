package com.examople.demo;

import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication.Locator;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableGemFireProperties;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.EnableSsl;
import org.springframework.data.gemfire.config.annotation.EnableSsl.Component;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@SpringBootApplication
@ClientCacheApplication(logLevel = "CONFIG", prSingleHopEnabled = true, readTimeout = 10000, retryAttempts = -1, maxConnections = 50, minConnections = 1, locators = {
		@Locator(host = "localhost", port = 10334) })
@EnablePdx(readSerialized = false, serializerBeanName = "compositePdxSerializer")
@EnableGemFireProperties(name = "ClientExample")
@EnableEntityDefinedRegions(basePackages = "com.example.demo.domain")
@EnableGemfireRepositories(basePackages = "com.example.demo.repo")
@EnableSsl(components = { Component.LOCATOR, Component.SERVER }, keystore="keystore.jks" , keystorePassword="password")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	PdxSerializer compositePdxSerializer() {
		return new ReflectionBasedAutoSerializer();
	}
}
