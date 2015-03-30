# author = Kirill Korovin (skvoter46@gmail.com)

from endpoints_proto_datastore.ndb import EndpointsModel
from google.appengine.ext import ndb


class Item(EndpointsModel):

    _message_fields_schema = ('id', 'header', 'dormitory', 'description', 'category', 'contacts', 'starttime',
                              'endtime', 'currentp', 'limitp', 'room')

    header = ndb.StringProperty()
    description = ndb.StringProperty()
    category = ndb.StringProperty()
    contacts = ndb.StringProperty()
    starttime = ndb.DateTimeProperty()
    endtime = ndb.DateTimeProperty()
    currentp = ndb.IntegerProperty()
    limitp = ndb.IntegerProperty()
    dormitory = ndb.IntegerProperty()
    room = ndb.IntegerProperty()


class Response(EndpointsModel):
    message = ndb.StringProperty()


class CountResponse(EndpointsModel):
    dorm7 = ndb.IntegerProperty()
    dorm91 = ndb.IntegerProperty()
    dorm92 = ndb.IntegerProperty()
