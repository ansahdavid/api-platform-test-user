/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.testuser.models

import java.time.LocalDate
import uk.gov.hmrc.testuser.models.ServiceKeys._
import uk.gov.hmrc.domain.Nino

case class AuthenticationRequest(username: String, password: String)

case class AuthenticationResponse(gatewayToken: String, affinityGroup: String)

case class AuthSession(authBearerToken: String, authorityUri: String, gatewayToken: String)

case class CreateUserWithOptionalRequestParams(
    serviceNames: Option[Seq[ServiceKey]],
    eoriNumber: Option[EoriNumber],
    nino: Option[Nino],
    taxpayerType: Option[TaxpayerType]
  )

case class CreateUserRequest(serviceNames: Option[Seq[ServiceKey]])

object LegacySandboxUser {
  private val userId               = "user1"
  private val groupIdentifier      = Some("groupIdentifier1")
  private val password             = "password1"
  private val userFullName         = "John Doe"
  private val emailAddress         = "john.doe@example.com"
  val sandboxAuthenticationRequest = AuthenticationRequest(userId, password)
  val individualDetails            = IndividualDetails("John", "Doe", LocalDate.parse("1980-01-10"), Address("221b Baker St", "Marylebone", "NW1 6XE"))

  val sandboxUser                  = TestIndividual(
    userId = userId,
    password = password,
    userFullName = userFullName,
    emailAddress = emailAddress,
    individualDetails = individualDetails,
    saUtr = Some("1700000000"),
    nino = Some("AA000017A"),
    groupIdentifier = groupIdentifier,
    services = Seq(NATIONAL_INSURANCE, SELF_ASSESSMENT)
  )
}

case class InvalidCredentials(msg: String)        extends Exception
case class UserNotFound(userType: UserType.Value) extends Exception

object ErrorCode extends Enumeration {

  type ErrorCode = Value

  val INTERNAL_SERVER_ERROR = Value("INTERNAL_SERVER_ERROR")
  val INVALID_CREDENTIALS   = Value("INVALID_CREDENTIALS")
  val USER_NOT_FOUND        = Value("USER_NOT_FOUND")
  val NINO_ALREADY_USED     = Value("NINO_ALREADY_USED")
}

case class ErrorResponse(code: ErrorCode.Value, message: String)

object ErrorResponse {
  val internalServerError       = ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, "An unexpected error occurred")
  val invalidCredentialsError   = ErrorResponse(ErrorCode.INVALID_CREDENTIALS, "Invalid Authentication information provided")
  val individualNotFoundError   = ErrorResponse(ErrorCode.USER_NOT_FOUND, "The individual can not be found")
  val organisationNotFoundError = ErrorResponse(ErrorCode.USER_NOT_FOUND, "The organisation can not be found")
  val ninoAlreadyUsed           = ErrorResponse(ErrorCode.NINO_ALREADY_USED, "The nino specified has already been used")
}
