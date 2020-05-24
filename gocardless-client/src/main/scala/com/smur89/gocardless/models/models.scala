package com.smur89.gocardless.models

import io.circe.generic.extras

package object models {
  implicit val codecConfiguration: extras.Configuration =
    extras.Configuration.default.withDiscriminator("type").withSnakeCaseMemberNames
}
