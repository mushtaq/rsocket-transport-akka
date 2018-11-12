/*
 * Copyright 2015-2018 the original author or authors.
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

package io.rsocket.transport.akka;

import io.rsocket.Frame;
import io.rsocket.RSocketFactory;
import io.rsocket.test.PingHandler;
import io.rsocket.transport.netty.server.TcpServerTransport;

public final class NettyTcpPongServer {

  public static void main(String... args) {
    RSocketFactory.receive()
        .frameDecoder(Frame::retain)
        .acceptor(new PingHandler())
        .transport(TcpServerTransport.create("0.0.0.0", 7878))
        .start()
        .block()
        .onClose()
        .block();
  }
}
