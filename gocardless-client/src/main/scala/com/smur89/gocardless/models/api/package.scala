package com.smur89.gocardless.models

import io.circe.generic.extras.Configuration

package object api {
  implicit val codecConfiguration: Configuration = Configuration.default.withSnakeCaseMemberNames
}
