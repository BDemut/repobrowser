# Repo Browser

Aplikacja będąca rozwiązaniem zadania rekrutacyjnego nr 1 (Mobile Software Developer) Allegro Summer e-Xperience. Służy ona do wyświetlania repozytoriów Github danego użytkownika.

Aplikacja została stworzona w architekturze MVVM - pobiera dane z API Githuba, przechowuje je w bazie danych, i za pomocą repozytorium udostępnia dwóm ekranom:

Ekran wypisujący listę wszystkich repozytorów użytkownika, wraz z podstawowymi informacjami:

<img src="https://github.com/BDemut/repobrowser/blob/main/list.jpg" alt="list screenshot" width="360" height="640">

Ekran wypisujący szczegóły konkretnego repozytorium:

<img src="https://github.com/BDemut/repobrowser/blob/main/detes.jpg" alt="details screenshot" width="360" height="640">

Aplikacja została poszerzona o funkcjonalność wyboru użytkownika, którego repozytoria chcemy wyświetlić.

# Szczegóły architektury

Activity
-
Aplikacja korzysta z jednej klasy Activity. Wykorzystywana jest jako rodzic dla Fragmentu 'NavHostFragment' zarządzającego nawigacją.

Fragment
-
Aplikacja posiada 2 Fragmenty - po jednym dla każdego ekranu. Klasy te obsługują wszystkie interakcje z elementami UI jak również obserwują zmiany w obiektach LiveData w odpowiadającym im ViewModelom. Każdy ekran ma swój Fragment, ViewModel i odpowiadający mu model domeny.

ViewModel
-
Posiadają dostęp do klasy Repository - pobierają z niej dane i udostępniają Fragmentom w formie LiveData oraz wywołują jej funkcje (oba ViewModele mają dostęp do tego samego obiektu Repository)

Repository
-
Obiekt typu singleton (o zasięgu aplikacji - jest on udostępniany ViewModelom za pomocą biblioteki Hilt), zarządza danymi aplikacji. Pobiera dane z sieci i udostępnia je poprzez bazę danych. Obsługuje również SharedPrefferences (przechowywana jest tam nazwa użytkownika).

ApiService
-
Interfejs biblioteki Retrofit służący do pobierania danych z API Githuba.

Database
-
Baza danych biblioteki Room służąca jako offline cache danych pobranych z Githuba.

# Szczegóły implementacji

Struktura folderów w rozwiązaniu została zaprojektowana aby odzwierciedlić architekturę aplikacji (np. pliki dotyczące jednego ekranu są zgrupowane razem).

ListFragment służy jako element początkowy w hierarchii nawigacji. Wyświetla on informacje o repozytoriach za pomocą RecyclerView i reaguje na eventy UI. Po naciśnięciu ikonki lupy, wywołuje SearchUserDialog, który służy do wprowadzenia nazwy uzytkownika. ListViewModel przechowuje informacje o repozytoriach, w formie klasy ShortRepo zawierającej podstawowe dane. Wywołuje on również odpowiednie funkcje w klasie Repository po wprowadzeniu nowej nazwy użytkownika lub wciśnięciu ikonki odświeżenia.

Po wybraniu elementu w ListFragment, za pomocą SafeArgs przekazywane jest ID repozytorium do DetailsFragment. Następnie, DetailsViewModel pobiera z Repository dane dotyczące repozytorium o tym ID w formie obiektu LongRepo. DetailsFragment odczytuje te dane i wyświetla je na ekranie. Jeżeli brakuje danych o językach, wywołuje funkcję w DetailsViewModel, która zleca Repository pobranie ich z Github API. DetailsFragment pozwala również na otworzenie strony repozytorium w przeglądarce lub aplikacji Github za pomocą startActivity().

Klasa Repository ma tylko jedną instancję wspólną dla całej aplikacji. Posiada ona wyłączny dostęp do bazy danych i interfejsu API. Działa ona na zasadzie 'single source of truth' - dane pobierane z internetu umieszczane są w bazie danych, i obiekt Repository udostępnia tylko i wyłącznie dane z bazy danych. Obiekt ten jest również odpowiedzialny za konwertowanie obiektów przedstawiających repozytoria z jednego typu 'data class' w inny (w niektórych przypadkach jest to uproszczone gdy DAO poprzez polecenie SELECT zwraca bezpośrednio model domeny).

# Kolejne iteracje projektu
Jak można rozwinąć projekt w kolejnych iteracjach:

Autoryzacja użytkownika
-
Głównym problemem aplikacji jest limit API Githuba ograniczający liczbę poleceń jakie można wysłać do 60 na godzinę. Jest to mało, biorąc pod uwagę to, że aplikacja wykonuje zapytanie za każdym razem gdy wyświetlane są szczegóły repozytorium (o ile nie posiada ich już w bazie danych). Dla autoryzowanych użytkowników limit ten rośnie do ponad 1000 w zależności od rodzaju autoryzacji, co praktycznie samo w sobie eliminuje ten problem. Dodatkowo autoryzacja pozwala na dodanie funkcji takich jak np. wyświetlanie prywatnych repozytoriów.

Przechowywanie informacji o więcej niż 1 użytkowniku
-
Innym sposobem w jaki można zaadresować powyższy problem jest rozszerzenie funkcjonalności offline cachingu. Aktualnie, za każdym razem, gdy wyszukiwany jest nowy użytkownik baza danych jest całkowicie czyszczona - zamiast tego można by przechowywać dane np. często odwiedzanych użytkowników lub kilku ostatnio odwiedzonych użytkowników.

Więcej detali
-
Ekran DetailsFragment wyświetla minimum informacji potrzebnych do oszacowania popularności, aktualności i budowy projektu. W kolejnych iteracjach można rozwinąć ten ekran aby zawierał więcej informacji, np. listę osób tworzących repozytorium a nawet plik README.

Filtrowanie i sortowanie
-
Przy wyświetlaniu repozytoriów użytkownika posiadającego ich wiele (jak Allegro), bardzo przydatna była by funkcja filtrowania (po nazwie, opisie lub języku programowania) i sortowania wg różnych kryteriów, aby łatwiej znaleść repozytorium którego szukamy.

Test Driven Development
-
Aby w ograniczonym zadanym czasie skupić się jak najbardziej na pisaniu "kodu właściwego" aplikacji, w dużej części zaniedbałem pisanie testów automatycznych na rzecz testów manualnych. O ile na krótką metę pozwala to zaoszczędzić trochę czasu, może sporo kosztować w przyszłości, więc przy dalszym rozwoju aplikacji należało by napisać odpowiednie testy.
