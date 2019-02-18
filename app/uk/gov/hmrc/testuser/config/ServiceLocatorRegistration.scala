/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.testuser.config

import com.google.inject.AbstractModule
import uk.gov.hmrc.testuser.connectors.ServiceLocatorConnector
import javax.inject.{Inject, Singleton}
import play.api.Logger

class ServiceLocatorRegistrationModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ServiceLocatorRegistration]).asEagerSingleton()
  }
}

@Singleton
class ServiceLocatorRegistration @Inject()(config: ServiceLocatorRegistrationConfig, connector: ServiceLocatorConnector) {

  if (config.registrationEnabled) {
    Logger.info(s"Service Locator registration is enabled")
    connector.register()
  } else {
    Logger.info(s"Service Locator registration is disabled")
  }
}

case class ServiceLocatorRegistrationConfig(registrationEnabled: Boolean)