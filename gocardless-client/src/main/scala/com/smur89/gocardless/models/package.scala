package com.smur89.gocardless

import io.circe.generic.extras

package object gocardless {

  implicit val codecConfiguration: extras.Configuration =
    extras.Configuration.default.withDiscriminator("type").withSnakeCaseMemberNames

}
