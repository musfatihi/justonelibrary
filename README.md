# justonelibrary

La bibliothèque municipale de la ville de paris rencontre plusieurs problèmes liés à la gestion
manuelle des livres et à l'absence d'un système de suivi efficace. Ces problèmes se
traduisent particulièrement par les difficultés suivantes:
1. Gestion inefficace : Les bibliothécaires doivent effectuer toutes les tâches liées à la
gestion des livres manuellement, ce qui peut entraîner des erreurs et une perte de
temps. Il est difficile de maintenir une base de données à jour et de suivre l'état de
chaque livre (disponible ou emprunté).
2. Difficultés de recherche : Les utilisateurs de la bibliothèque ont du mal à trouver les
livres qu'ils souhaitent emprunter. L'absence d'un système de recherche efficace
rend la recherche fastidieuse et peut décourager les utilisateurs.
3. Manque de statistiques : Il n'existe aucun moyen facile d'obtenir des statistiques sur
les livres disponibles, les livres empruntés et les livres perdus. Cela limite la capacité
de la bibliothèque à analyser et à optimiser ses collections.

En résumé, le besoin est de développer une application console de gestion des
bibliothèques en Java, qui permettra une gestion automatisée des livres, une recherche
efficace, une gestion des emprunts et des retours, ainsi que la génération de rapports
statistiques. Cette application vise à résoudre les problèmes de gestion inefficace et de
difficulté de recherche rencontrés par la bibliothèque, tout en améliorant l'expérience des
utilisateurs.

Pour mettre en oeuvre ce projet j'ai bien suivi les principes SOLID permettant d'avoir un code bien structuré, evolutif et aisément maintenable. 
Pour la connexion à la base de données j'ai adopté le design pattern sigleton permettant de mieux optimiser la consommation de ressources. 

De coté database j'ai bien mis en place des triggers permettant d'automatiser le changement de status des livres disponible à la bibiothèque qui seront déclenchés après chaque opétration d'emprunt.

Pour mettre les livres comme perdus l'idée était de s'appuyer sur pgagent qui executera à chaque minuit une requete sql visant la detection des livres empruntés et qui ne sont pas retournés à la date prévue.
