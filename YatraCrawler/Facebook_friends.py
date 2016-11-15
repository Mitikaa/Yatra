import facebook
import requests


def get_all_friends(token ,allfriends):
    graph=facebook.GraphAPI(token)
    friends=graph.get_object('me/friends',fields='id,name,location')

    while (True):
        for friend in friends['data']:
            try:
                name=friend["name"]
                loc=friend["location"]["name"]
                allfriends[friend["id"]]={
                    "name":name,
                    "location":loc,
                }
            except KeyError:
                pass
        try:
            friends=requests.get(friends['paging']['next']).json()
        except KeyError:
            break


def create_friends_instances(friends):
    instances = []
    instances.append("<?xml version=\"1.0\"?>\n")
    instances.append("<!DOCTYPE rdf:RDF [\n")
    instances.append("\t <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" > \n")
    instances.append("\t <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" > \n")
    instances.append("\t <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" > \n")
    instances.append("\t <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" > \n")
    instances.append("\t <!ENTITY sc \"http://www.semanticweb.org/ontologies/2016/12/Yatra\" > \n")
    instances.append("]> \n")
    instances.append("<rdf:RDF\n")
    instances.append("\t xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" \n")
    instances.append("\t xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" \n")
    instances.append("\t xmlns:owl=\"http://www.w3.org/2002/07/owl#\" \n")
    instances.append("\t xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" \n")
    instances.append("\t xmlns:sc=\"http://www.semanticweb.org/ontologies/2016/12/Yatra\">\n\n")

    i = 1
    for key, friend in friends.iteritems():
        instances += create_friend_rdf(friend, i)
        i += 1
    instances.append("</rdf:RDF>")
    return "".join(instances)

def create_friend_rdf(friend, i):
    rdf = []
    rdf.append("\t <Description rdf:about=\"&sc;person" + str(i) + "\">\n")
    rdf.append("\t <rdf:type rdf:resource=\"&sc;Person\"/>\n")
    rdf.append("\t <sc:hasName rdf:datatype=\"&xsd;string\">" + friend["name"] + "</sc:hasName>\n")
    rdf.append("\t <sc:hasLocation rdf:datatype=\"&xsd;string\">" + friend["location"] + "</sc:hasLocation>\n")
    rdf.append("\t <sc:isFriendOf rdf:resource=\"&sc;person26\"/>\n")
    rdf.append("\t </Description>\n\n")
    return "".join(rdf)


if __name__ == "__main__":
    tokens = [
        'EAACEdEose0cBACtanGh2XdEquhgb2K046QHsKM7wIosQwXkB6gipAKnrGqYcpgN7aCSFC27sxfF73QDRoKuFEoti9pROOkwoYk4cVfk7br8vvRcda2J3qGWv1jXPmRUYx8xvcsVxo9BxfNoRPTe2KeHSm85mixXti7faewZDZD',
        'EAACEdEose0cBAJq4A8cE7BVVIhGnhOX06rSEp2HOA40PTow5NLMVq8SPVAwH80lpAELFL95mDrOvzKzWEuHkVjwje4hyZBGodEQOgqCEO4YNEI2pNLZAtwXjZCpm2MH0oXJOPhKwisL1rswKv2c64ZCNmdsdBDqMyGFLYJlC6gZDZD',
        'EAACEdEose0cBAIywAljcD2PZAkcXICmxC42h4VEbtVCZAq1ZBJzMZBbtIdpTGOuyAtClM1gQYZBJzInxKFDXMnoCwFtEW8YmYMm0FeMLI5P6VlUHRfcGt7N969ZBVcOZAtzpI9BK4zEZAqz16oGuBMZBr55bqE6UjWCFHHh8WBuj9RQZDZD',
        'EAACEdEose0cBAC1jVCcqtGTgeFD8v7LzIUlJbZBa9kfc6mkM8iZB6PJ7dwHHIExIfeRTgicXCR4ZASSOXbo3CF9iscB0jzScnrDC1tZADWBBhJN8SN8sxOuqw5zgUvUSukhVT3BIY34ZBHSrxfMKgRcoaF9HPh70J2NQvTgEVigZDZD',
        'EAACEdEose0cBAEnjuZBZC8kKsX40wusUEnPgFnk1CuGWkedqXhQ1IOZAjHVFEjwztMwww2ZBWV00topK5vrC8c51JnTPMpD0peS155ggwrH7mRIzSw4ZASPJw8ZAq3m1aOiQZBfe1DS6kL4p3dApNEJJZCLO1UMxlVhkz6BjdcBRRQZDZD',
        'EAACEdEose0cBAOMZAsDzTdDSlPCnElZAdr6IKrL6w06I1zSsZAx7kTk9OYxrGZArxzquVWqBG2bAFNjDnZBqRNk55fdRSxrw18iigWrRfru4y1tnH9cGtHsCowSZB2TC0WoHOz8Q5j6AqZCGUeHGTkA3gYBnQpkBSxkv5xElVHjoAZDZD',
        'EAACEdEose0cBAOWBCbZBZAdjlV5lKQQd7k9IaaFKpTt4g7B1bXEdzRKIoG0MWlsDmzboRTd2ZBEX93BB1WSjTgkXSNtob8sEjh9fmadqZCiDd2ZCv5hqMxkCDaBFNA5hg3jt4Sm54jCWxBFcrVRjirZA7Uz3KVHpzqOGCTV2crtQZDZD',
        'EAACEdEose0cBAOplJOKb5PZCrxNfGksbTGE9ldVWsSmyKoNP2oaFwQAbYrb1ZA5qZC7Jwa748NfmpZC1ryJML8JU6oCqx9qcCMRC1lsNec62ATRBZCLSs96MJwvXvlbrd35esh8ZAeEHHe2tkalc3A3vkNpb4UWgh45IYQhZBEXlgZDZD',
        'EAACEdEose0cBAAZBqZAA3Ea4JoDoNQRCtoZAsnFHyg8LtAPfhe7CJStZCtWRsZCI36u6ZBRco0EKMb16mZApEooH3liZBhWonxDfhy65TMDOslDiTCWdsC3cNrYGFnN04pvNHESAlYUvjhHjDZAUZBmyyidjNZBscypZByTBBwmGPlRTpAZDZD']
    allfriends = {}
    for token in tokens:
        get_all_friends(token, allfriends)

        instances = create_friends_instances(allfriends)
        print instances
        text_file = open("friends.rdf", "w")
        text_file.write(instances)
        text_file.close()