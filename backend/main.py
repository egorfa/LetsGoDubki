#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# author = Kirill Korovin (skvoter46@gmail.com)



from models import Item, Response, CountResponse
import endpoints
from google.appengine.ext import ndb
from protorpc import remote
import datetime


@endpoints.api(name='dubkiapi', version='v1', description='LetsGoDubki app backend api',
               audiences=[endpoints.API_EXPLORER_CLIENT_ID])
class DubkiApi(remote.Service):
    # Item methods
    @Item.method(path='item', http_method='POST', name='item.insert')
    def item_insert(self, my_item):
        my_item.put()
        return my_item

    @Item.method(request_fields=('id', ),
                 path='itemsubscribe/{id}', http_method='GET', name='item.subscribe')
    def item_subscribe(self, my_item):
        if my_item.from_datastore:
            if my_item.currentp < my_item.limitp:
                my_item.currentp += 1
                my_item.put()
            else:
                raise endpoints.ServiceException('Limit is overflow')
        else:
            raise endpoints.NotFoundException('Item not found')
        return my_item

    @Item.method(request_fields=('id', ),
                 path='item/{id}', http_method='GET', name = 'item.get')
    def item_get(self, my_item):
        if not my_item.from_datastore:
            raise endpoints.NotFoundException('Item not found')
        return my_item

    @Item.method(request_fields=('id', ),
                 path='itemdelete/{id}', http_method='GET', name = 'item.deleteid',
                 response_message=Response.ProtoModel())
    def item_delete(self, my_item):
        if not my_item.from_datastore:
            raise endpoints.NotFoundException('Item not found')
        else:
            my_item.key.delete()
            response = Response(message='200 OK')
        return response.ToMessage()

    @Item.method(path='itemscount', name='items.count', http_method='GET',
                 response_message=CountResponse.ProtoModel())
    def item_count(self, my_item):
        response = CountResponse(dorm7=my_item.query().filter(Item.dormitory == 7).count(),
                                 dorm91=my_item.query().filter(Item.dormitory == 91).count(),
                                 dorm92=my_item.query().filter(Item.dormitory == 92).count(),)
        return response.ToMessage()

    # Item query methods
    @Item.query_method(path='items/deleteall', name='items.deleteall')
    def items_delete(self, query):
        ndb.delete_multi(Item.query().fetch(keys_only=True))
        return query

    @Item.query_method(path='items/nonactualdelete', name='items.deletenonactual')
    def nonactual_items_delete(self, query):
        ndb.delete_multi(Item.query().filter(Item.endtime < datetime.datetime.now()).fetch(keys_only=True))
        return query

    @Item.query_method(query_fields=('dormitory', 'limit', 'order', 'pageToken'),
                       path='myitems', name='item.list')
    def item_list(self, query):
        return query


application = endpoints.api_server([DubkiApi], restricted=False)