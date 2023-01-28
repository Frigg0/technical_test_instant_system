--- Exercice :
Le but de l’exercice est de :  Développer une application “serveur” exposant une API REST pour permettre à une application mobile, ou un site Web, d’afficher la liste des parkings à proximité comme illustré sur l’écran en PJ.

Ici, on utilisera la source de données suivante disponible à Poitiers pour récupérer la liste des parkings : https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilite-parkings-grand-poitiers-donnees-metiers&rows=1000&facet=nom_du_parking&facet=zone_tarifaire&facet=statut2&facet=statut3
Le nombre de places disponibles du parking en temps réel est récupérable via : https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom

Consignes :
L’application doit pouvoir fonctionner dans d’autres villes : l’URL et format des données parking pourront donc être complètement différents, cependant l’API REST exposée pour l’application mobile ne devra pas évoluer.
L’application à développer dans le cadre de l’exercice comporte uniquement la partie serveur (il n’est pas demandé de développer l’application mobile).

Les livrables que nous attendons sont :

Le code source de l’application (via Github par exemple)
Un document (type Readme) expliquant :
les choix que vous avez faits,
Les problèmes que vous n’avez peut-être pas traités, mais que vous avez identifiés,
Toute autre information utile pour apprécier votre travail.

Remarques sur les choix effectués / potentiels implémentations futurs


---
Voici le resultat du GET :
[{"name":"PALAIS DE JUSTICE","capacity":70,"placesAvailable":69,"distance":4162710},{"name":"SAINTE CROIX","capacity":null,"placesAvailable":null,"distance":4162085},{"name":"MERIMEE","capacity":null,"placesAvailable":null,"distance":4163251},{"name":"JEAN DE BERRY","capacity":null,"placesAvailable":null,"distance":4163411},{"name":"BLOSSAC TISON","capacity":665,"placesAvailable":488,"distance":4161845},{"name":"CLAIN SAINTE RADEGONDE","capacity":null,"placesAvailable":null,"distance":4161984},{"name":"LIBERTE","capacity":null,"placesAvailable":null,"distance":4162864},{"name":"CLOS DES CARMES","capacity":null,"placesAvailable":null,"distance":4162527},{"name":"JARDIN DES PLANTES","capacity":null,"placesAvailable":null,"distance":4162883},{"name":"ANATOLE FRANCE","capacity":null,"placesAvailable":null,"distance":4161879},{"name":"INTENDANT LE NAIN","capacity":null,"placesAvailable":null,"distance":4163459},{"name":"GRAND CERF","capacity":null,"placesAvailable":null,"distance":4163115},{"name":"MONTIERNEUF","capacity":null,"placesAvailable":null,"distance":4163214},{"name":"ARRET MINUTE","capacity":137,"placesAvailable":120,"distance":4162824},{"name":"CLAIN BAJON","capacity":null,"placesAvailable":null,"distance":4162326},{"name":"PASTEUR","capacity":null,"placesAvailable":null,"distance":4161590},{"name":"SOUS BLOSSAC","capacity":null,"placesAvailable":null,"distance":4161801},{"name":"MONTFORT","capacity":null,"placesAvailable":null,"distance":4163159},{"name":"THEATRE","capacity":320,"placesAvailable":204,"distance":4162769},{"name":"GARE TOUMAI","capacity":632,"placesAvailable":405,"distance":4162805},{"name":"NOTRE DAME","capacity":642,"placesAvailable":420,"distance":4162580},{"name":"CHASSEIGNE","capacity":null,"placesAvailable":null,"distance":4163125},{"name":"MADELEINE","capacity":null,"placesAvailable":null,"distance":4161746},{"name":"PLACE DE LA CATHEDRALE","capacity":null,"placesAvailable":null,"distance":4162201},{"name":"MAILLOCHON","capacity":null,"placesAvailable":null,"distance":4162872},{"name":"HOTEL DE VILLE","capacity":625,"placesAvailable":329,"distance":4162271}]


Temps passé: environ 5h
Difficultés rencontrées:
 * Manipulation des points géo pour calcul des distances
 * Mise en place de Mockito (pas encore expert, mais bon exercice)
---
Remarques:
 * Les data ne match pas en permanence, il manque SAINTE CROIX par exemple sur l'api location
 * Pas assez de temps pour mettre en place des test sur le controller

Pour la suite / ce que j'aurai pu faire:
 * URL potentiels futur : Aujourd'hui ces URL sont fournis en dur dans un fichier de configuration apiProvider.properties
Puis ensuite dans le controller vient chercher la bonne valeur en regardant le paramètre donné en request: locationCityName.
On peut tout à fait imaginer avoir une DB qui manage ces URL afin d'éviter tout déploiement lorsque celles-ci doivent évoluer ou être modifier.
 * UML data scheme to have a global vision of the pattern
 * Récupérer plus de data si le besoin évolue (les tarifs, etc)
 * Implementer des tests d'intégration / Non régression automatique
 * Renforcer les tests unitaires: la gestion des erreurs, rajouter des edge cases
 * Potentiellement Mettre en place de l'authentification sur le controler / permet de contrôler la consommation de celui-ci
 * Implémenter Kibana et autres outils pour une meilleure maintenance opérative
Tout en améliorant les logs pour récupérer les données du temps écoulé si besoin de monitoring des performances externes
 * Mise en place d'un pipeline CI/CD
 Avec outil pour dockeriser ou autre pour facilité un scalabilité applcative / déploiement cloud
 * Avec intégration outillage pour qualité de code (ex: SonarQube)
 * Mettre en place un caching :
 J'imagine que les données de base d'un parking ne change pas souvent (nom, lieu etc)
 On peut avoir du cache de ces infos basiques, ne nécessitant de n'appeler plus qu'une seul API a chaque requete
 A voir si on pourrait également faire un cache plus court rapport aux places dispo
