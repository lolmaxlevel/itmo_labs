t = ['t', 't side', 't-side', 'т', 'terrorists', 'terorists', 'terrorist', 'теры', 'террористы']
ct = ['ct', 'ct side', 'ct-side', 'кт', 'counter_terrorists', 'counter-terrorists', 'counter-terrorists',
      'контры', 'контр террористы']

valid_budgets = ['cheap', 'eco']

valid_weapon_types = ['rifle', 'sniper', 'pistol', 'machine_gun', 'any']


def get_user_input():
    print("""
    Добро пожаловать в лучший в мире помощник по выбору оружия в контер страйк 2!
    
    Сейчас мы у вас спросим несколько вопросов, отвечайте честно и в формате который просят, спасибо!
    
    Приятного использования!
    """)
    return ask_side(), ask_budget(), ask_type()


def ask_side():
    side = input("Введите сторону (`terrorists`, `counter_terrorists`, `ct`, `t` и подобное): \n>").strip().lower()
    if side in ct:
        print("Bingo, bango, bongo; bish, bash, bosh.")
        return 'counter_terrorists'
    elif side in t:
        print("We will make them cry!")
        return 'terrorists'
    else:
        print("Неверный ввод, следуйте указаниям в скобках!")
        return ask_side()


def ask_budget():
    budget = input("Введите в какой бюджет мы выбираем оружие `eco` если эко раунд, "
                   "`cheap` если пистолетный раунд или совсем нет денег. Или просто целое число: \n>").strip().lower()
    if budget in valid_budgets:
        return budget
    elif int(budget):
        if int(budget) <= 0:
            print("Боюсь в такую стоимость мы ничего не найдем(")
            return ask_budget()
        else:
            return budget
    else:
        print("Неверный ввод, следуйте указаниям в скобках!")
        return ask_budget()
    pass


def ask_type():
    weapon_type = input("Введите какое оружие предпочитаете, возможные варианты - " + ", ".join(
        valid_weapon_types) + ":\n>").strip().lower()
    if weapon_type in valid_weapon_types:
        print("Отличный выбор!")
        return weapon_type
    else:
        print("Неверный ввод, следуйте указаниям в скобках!")
        return ask_type()
    pass
