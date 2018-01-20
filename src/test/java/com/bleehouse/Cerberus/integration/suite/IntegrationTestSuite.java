package com.bleehouse.Cerberus.integration.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bleehouse.Cerberus.integration.controller.rest.AuthenticationControllerTest;
import com.bleehouse.Cerberus.integration.controller.rest.ProtectedControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AuthenticationControllerTest.class,
  ProtectedControllerTest.class
})
public class IntegrationTestSuite {

}
