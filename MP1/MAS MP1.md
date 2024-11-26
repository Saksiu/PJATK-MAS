**Cel**: Stworzenie prostego systemu do zarządzania księgozbiorem w bibliotece.

- **Ekstensja i trwałość**: Przechowywanie listy wszystkich książek i użytkowników. Można tu zastosować serializację do zapisu i odczytu stanu biblioteki z pliku.
- **Atrybut złożony**: Informacje o książce (tytuł, autor, rok wydania).
- **Atrybut opcjonalny**: Data zwrotu (może nie być ustawiona, jeśli książka nie jest wypożyczona).
- **Atrybut powtarzalny**: Lista wypożyczeń dla użytkownika.
- **Atrybut klasowy**: Licznik wszystkich książek w systemie.
- **Atrybut pochodny**: Liczba dostępnych książek (łączna liczba książek minus wypożyczone).
- **Metoda klasowa**: Wyszukiwanie książek po tytule lub autorze.
- **Przesłonięcie, przeciążenie**: Metody do wypożyczania książek, które mogą być przeciążone na różne sposoby (np. po ID książki, tytule).