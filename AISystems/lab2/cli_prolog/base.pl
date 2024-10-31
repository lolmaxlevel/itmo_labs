% Оружие как факт с одним аргументом
weapon("Glock-18").
weapon("USP-S").
weapon("P2000").
weapon("P250").
weapon("Five-SeveN").
weapon("Tec-9").
weapon("Desert Eagle").
weapon("Dual Berettas").
weapon("CZ75-Auto").
weapon("AK-47").
weapon("M4A4").
weapon("M4A1-S").
weapon("Galil AR").
weapon("FAMAS").
weapon("SG 553").
weapon("AUG").
weapon("AWP").
weapon("SSG 08").
weapon("SCAR-20").
weapon("G3SG1").
weapon("M249").
weapon("Negev").
weapon("Nova").
weapon("XM1014").
weapon("MAG-7").
weapon("Sawed-Off").

% Гранаты
weapon("Flashbang").
weapon("HE Grenade").
weapon("Smoke Grenade").
weapon("Molotov").
weapon("Incendiary Grenade").
weapon("Decoy Grenade").

% Тип оружия
weapon_type("Glock-18", pistol).
weapon_type("USP-S", pistol).
weapon_type("P2000", pistol).
weapon_type("P250", pistol).
weapon_type("Five-SeveN", pistol).
weapon_type("Tec-9", pistol).
weapon_type("Desert Eagle", pistol).
weapon_type("Dual Berettas", pistol).
weapon_type("CZ75-Auto", pistol).
weapon_type("AK-47", rifle).
weapon_type("M4A4", rifle).
weapon_type("M4A1-S", rifle).
weapon_type("Galil AR", rifle).
weapon_type("FAMAS", rifle).
weapon_type("SG 553", rifle).
weapon_type("AUG", rifle).
weapon_type("AWP", sniper).
weapon_type("SSG 08", sniper).
weapon_type("SCAR-20", sniper).
weapon_type("G3SG1", sniper).
weapon_type("M249", machine_gun).
weapon_type("Negev", machine_gun).
weapon_type("Nova", shotgun).
weapon_type("XM1014", shotgun).
weapon_type("MAG-7", shotgun).
weapon_type("Sawed-Off", shotgun).

% Тип гранат
weapon_type("Flashbang", grenade).
weapon_type("HE Grenade", grenade).
weapon_type("Smoke Grenade", grenade).
weapon_type("Molotov", grenade).
weapon_type("Incendiary Grenade", grenade).
weapon_type("Decoy Grenade", grenade).

% Сторона, за которую можно купить оружие
weapon_side("Glock-18", terrorists).
weapon_side("USP-S", counter_terrorists).
weapon_side("P2000", counter_terrorists).
weapon_side("P250", both).
weapon_side("Five-SeveN", counter_terrorists).
weapon_side("Tec-9", terrorists).
weapon_side("Desert Eagle", both).
weapon_side("Dual Berettas", both).
weapon_side("CZ75-Auto", both).
weapon_side("AK-47", terrorists).
weapon_side("M4A4", counter_terrorists).
weapon_side("M4A1-S", counter_terrorists).
weapon_side("Galil AR", terrorists).
weapon_side("FAMAS", counter_terrorists).
weapon_side("SG 553", terrorists).
weapon_side("AUG", counter_terrorists).
weapon_side("AWP", both).
weapon_side("SSG 08", both).
weapon_side("SCAR-20", counter_terrorists).
weapon_side("G3SG1", terrorists).
weapon_side("M249", both).
weapon_side("Negev", both).
weapon_side("Nova", both).
weapon_side("XM1014", both).
weapon_side("MAG-7", counter_terrorists).
weapon_side("Sawed-Off", terrorists).

% Сторона для гранат все доступны обеим сторонам
weapon_side("Flashbang", both).
weapon_side("HE Grenade", both).
weapon_side("Smoke Grenade", both).
weapon_side("Molotov", terrorists).
weapon_side("Incendiary Grenade", counter_terrorists).
weapon_side("Decoy Grenade", both).

% Цена оружия
weapon_price("Glock-18", 200).
weapon_price("USP-S", 200).
weapon_price("P2000", 200).
weapon_price("P250", 300).
weapon_price("Five-SeveN", 500).
weapon_price("Tec-9", 500).
weapon_price("Desert Eagle", 700).
weapon_price("Dual Berettas", 400).
weapon_price("CZ75-Auto", 500).
weapon_price("AK-47", 2700).
weapon_price("M4A4", 3100).
weapon_price("M4A1-S", 2900).
weapon_price("Galil AR", 1800).
weapon_price("FAMAS", 2050).
weapon_price("SG 553", 3000).
weapon_price("AUG", 3300).
weapon_price("AWP", 4750).
weapon_price("SSG 08", 1700).
weapon_price("SCAR-20", 5000).
weapon_price("G3SG1", 5000).
weapon_price("M249", 5200).
weapon_price("Negev", 1700).
weapon_price("Nova", 1050).
weapon_price("XM1014", 2000).
weapon_price("MAG-7", 1300).
weapon_price("Sawed-Off", 1100).

% Цена гранат
weapon_price("Flashbang", 200).
weapon_price("HE Grenade", 300).
weapon_price("Smoke Grenade", 300).
weapon_price("Molotov", 400).
weapon_price("Incendiary Grenade", 600).
weapon_price("Decoy Grenade", 50).

% Правило для поиска оружия до 1000 долларов
cheap_weapon(X) :- weapon_price(X, Price), Price =< 1000.

% Проверить, является ли оружие эко-раундовым (дешевле 2000$)
eco_weapon(Weapon) :-
    weapon_price(Weapon, Price),
    Price =< 2000.

% Правило для поиска оружия, доступного только террористам
terrorist_weapon(X) :- weapon_side(X, terrorists).

% Правило для поиска оружия, доступного только спецназу
counter_terrorist_weapon(X) :- weapon_side(X, counter_terrorists).

% Правило для поиска оружия, доступного обеим сторонам
both_side_weapon(X) :- weapon_side(X, both).

% Правило для поиска оружия по типу и стороне
weapon_by_type_and_side(Type, Side, X) :- weapon_type(X, Type), weapon_side(X, Side).

% Правило для поиска оружия по типу и цене до определенной суммы
weapon_by_type_and_price(Type, MaxPrice, X) :- weapon_type(X, Type), weapon_price(X, Price), Price =< MaxPrice.

% Найти самое дешевое оружие определенного типа
cheapest_weapon_by_type(Type, Weapon) :-
    weapon_type(Weapon, Type),
    weapon_price(Weapon, Price),
    \+ (weapon_type(OtherWeapon, Type),
        weapon_price(OtherWeapon, OtherPrice),
        OtherPrice < Price).

% Найти оружие в заданном ценовом диапазоне
weapon_in_range(MinPrice, MaxPrice, Weapon) :-
    weapon_price(Weapon, Price),
    Price >= MinPrice,
    Price =< MaxPrice.

% Найти альтернативное оружие (того же типа для противоположной команды)
alternative_weapon(Weapon, AltWeapon) :-
    weapon_type(Weapon, Type),
    weapon_type(AltWeapon, Type),
    weapon_side(Weapon, terrorists),
    weapon_side(AltWeapon, counter_terrorists).