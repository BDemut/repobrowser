# Repo Browser

Aplikacja będąca rozwiązaniem zadania rekrutacyjnego nr 1 (Mobile Software Developer) Allegro Summer e-Xperience. Służy ona do wyświetlania repozytoriów Github danego użytkownika.

W dalszej części tego opisu będę używał określeń
  - "repo" - repozytorium Github i jego reprezentacja w aplikacji
  - "repozytorium" - repozytorium jako obiekt udostępniający i zarządzający danymi w aplikacji

Aplikacja została stworzona w architekturze MVVM - pobiera dane z API Githuba, przechowuje je w bazie danych, i za pomocą repozytorium udostępnia dwóm ekranom:

Ekran wypisujący listę wszystkich repozytorów użytkownika, wraz z podstawowymi informacjami
*screenshot*

Ekran wypisujący szczegóły konkretnego repozytorium
*screenshot*

Aplikacja została poszerzona o funkcjonalność wyboru użytkownika, którego repo chcemy wyświetlić.

# Szczegóły implementacji i architektury

Activity
-
Aplikacja korzysta z jednej klasy Activity. Wykorzystywana jest jako rodzic dla Fragmentu 'NavHostFragment' zarządzającego nawigacją.

Fragment
-
Aplikacja posiada 2 Fragmenty - po jednym dla każdego ekranu. Klasy te obsługują wszystkie interakcje z elementami UI jak również obserwują zmiany w obiektach LiveData w odpowiadającym im ViewModelom. Po wyborze repo w ListFragment za pomocą SafeArgs wysyłane jest jego id do obiektu DetailsFragment.

ViewModel
-
Posiadają dostęp do klasy Repository - pobierają z niej dane i udostępniają Fragmentom oraz wywołują jej funkcje (oba ViewModele mają dostęp do tego samego obiektu Repository)

Repository
-
Obiekt typu singleton (zasięg aplikacji - jest udostępniany ViewModelom za pomocą biblioteki Hilt), zarządza danymi aplikacji. Pobiera dane z sieci i udostępnia je poprzez bazę danych oraz SharedPrefferences aplikacji (przechowywana jest tam informacja o użytkowniku).

ApiService
-
Interfejs biblioteki Retrofit służący do pobierania danych z API Githuba.

Database
-
Baza danych biblioteki Room służąca jako offline cache danych pobranych z Githuba.

Wielowątkowość obsługiwana jest przez Kotlin Coroutines a widoki zostały zaprojektowane przy użyciu Material Design.

# Kolejne iteracje projektu
Funkcje jakie można dodać w kolejnych iteracjach:

Autoryzacja użytkownika
-
Głównym problemem aplikacji jest limit API Githuba ograniczający liczbę poleceń jakie można wysłać do 60 na godzinę, co jest małą ilością biorąc pod uwagę to, że aplikacja wykonuje zapytanie za każdym razem gdy wyświetlane są szczegóły repo (o ile nie posiada ich już w bazie danych). Dla autoryzowanych użytkowników limit ten rośnie do ponad 1000 w zależności od rodzaju autoryzacji co praktycznie samo w sobie eliminuje ten problem. Dodatkowo autoryzacja pozwala na dodanie funkcji takich jak wyświetlanie prywatnych repozytoriów.

Przechowywanie informacji o więcej niż 1 użytkowniku
-
Innym sposobem w jaki można zaadresować powyższy problem jest rozszerzenie funkcjonalności offline cachingu. Aktualnie za każdym razem gdy wyszukiwany jest nowy użytkownik baza danych jest całkowicie czyszczona - zamiast tego można by przechowywać dane np. często odwiedzanych użytkowników lub kilku ostatnio odwiedzonych użytkowników.

Więcej detali
-
Ekran DetailFragmet wyświetla minimum informacji potrzebnych do oszacowania popularności, aktualności i budowy repo. W kolejnych iteracjach można rozwinąć ten ekran aby zawierał więcej informacji, np. listę osób tworzących repo a nawet plik README.

Filtrowanie i sortowanie
-
Przy wyświetlaniu repozytoriów użytkownika posiadającego ich wiele (jak Allegro), bardzo przydatna była by funkcja filtrowania (po nazwie, opisie lub języku programowania) i sortowania wg różnych kryteriów, aby łatwiej znaleść repo którego szukamy.
