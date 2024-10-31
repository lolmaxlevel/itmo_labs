import time

from pyswip import Prolog

from user_interface import get_user_input
from weapon_recomender import recommend_weapons

# Инициализация Prolog
prolog = Prolog()
prolog.consult("base.pl")
def main():
    side, budget, weapon_type = get_user_input()
    print("Сейчас посмотрим что мы можем предложить...")
    time.sleep(1.5)
    recommendations = recommend_weapons(side, budget, weapon_type)

    if recommendations:
        print("Рекомендованное оружие:")
        for weapon in recommendations:
            print(f"- {weapon}")
    else:
        print("Хм, не смогли ничего найти... Попробуй увеличить бюджет или сменить тип оружия.")

if __name__ == "__main__":
    main()