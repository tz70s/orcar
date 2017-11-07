//
// Copyright (c) 2017 original authors and authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

extern crate futures;
extern crate hyper;
extern crate tokio_core;

use self::hyper::Client;
use std::io::{self, Write};
use self::futures::{Future, Stream};
use self::tokio_core::reactor::Core;

pub struct Context {
    server_address: &'static str
}

impl Context {
    pub fn new(server_address_string: String) -> Self {
        let uri = server_address_string.parse().unwrap();
        Context {
            server_address: uri
        }
    }
}

pub struct Requests {
    context: &'static Context
}

impl Requests {
    pub fn new(context: &'static Context) -> Self {
        Requests {
            context
        }
    }
    pub fn get(&self) {
        let mut core = Core::new().unwrap();
        let client = Client::new(&core.handle());
        let work = client.get(self.context.server_address).and_then(|res| {
            println!("Response: {}", res.status());

            res.body().for_each(|chunk| {
                io::stdout()
                    .write_all(&chunk)
                    .map_err(From::from)
            })
        });
        core.run(work).unwrap();
    }
}
