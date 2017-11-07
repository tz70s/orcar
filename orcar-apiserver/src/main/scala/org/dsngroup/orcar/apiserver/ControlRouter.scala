/*
 * Copyright (c) 2017 original authors and authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dsngroup.orcar.apiserver

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.stream.ActorMaterializer
import scala.io.StdIn

/**
  * The control router is a default router middleware for api server.
  * Also, it acts as a dispatcher of actor system in api server.
  * TODO: may move this into an actor instead of actor system dispatcher?
  */
object ControlRouter {

  val route =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }

  def start(bindingAddress: String, bindingPort: Int): Unit = {
    implicit val system = ActorSystem("api-server")
    implicit val materializer = ActorMaterializer()

    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val bindingFuture = Http().bindAndHandle(route, bindingAddress, bindingPort)

    system.log.info(s"Server online at $bindingAddress:$bindingPort")

    // Infinite block
    // TODO: Consider a better way here.
    var done = true
    while (done) {
      StdIn.readLine() match {
        case "exit" => {
          // Unbind and terminate system
          bindingFuture
            .flatMap(_.unbind())
            .onComplete(_ => {
              system.terminate()
              done = false
            })
        }
      }
    }
  }
}
