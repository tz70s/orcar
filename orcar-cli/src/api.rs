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

//! This file records the api server context.

pub struct Context {
    server_address: &'static str,
}

pub struct API {
    context: Context,
}

/// The location trait has default methods of location creation.
/// Use trait for mixin style, currently.
pub trait Location {
    /// Create a location entity in api server.
    fn create_location(ip_address: &str, alias_name: &str) {
        // TODO: HTTP POST LOCATION
        Self::set_location_requests();
    }

    /// Delete a location entity in api server.
    fn delete_location(alias_name: &str) {
        // TODO: HTTP DELETE LOCATION
        Self::set_location_requests();
    }

    /// Need to be implemented in different context.
    fn set_location_requests();
}

/// The node trait has default methods of node creation.
/// Use trait for mixin style, currently.
pub trait Node {
    /// Create a node entity in api server.
    fn create_node(ip_address: &str, alias_name: &str) {
        // TODO: HTTP POST LOCATION
        Self::set_node_requests();
    }

    /// Delete a location entity in api server.
    fn delete_node(alias_name: &str) {
        // TODO: HTTP DELETE LOCATION
        Self::set_node_requests();
    }

    /// Need to be implemented in different context.
    fn set_node_requests();
}
