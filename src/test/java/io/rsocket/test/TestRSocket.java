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

package io.rsocket.test;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestRSocket extends AbstractRSocket {

  @Override
  public Mono<Payload> requestResponse(Payload payload) {
    return Mono.just(DefaultPayload.create("hello world", "metadata"));
  }

  @Override
  public Flux<Payload> requestStream(Payload payload) {
    return Flux.range(1, 10_000).flatMap(l -> requestResponse(payload));
  }

  @Override
  public Mono<Void> metadataPush(Payload payload) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> fireAndForget(Payload payload) {
    return Mono.empty();
  }

  @Override
  public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
    // TODO is defensive copy neccesary?
    return Flux.from(payloads).map(DefaultPayload::create);
  }
}
