from pyswip import Prolog
prolog = Prolog()
prolog.consult("base.pl")

def recommend_weapons(side, budget, weapon_type):
    recommendations = []

    # Формирование запроса на основе предпочтений пользователя
    if budget == "cheap":
        budget_query = "(cheap_weapon(X))"
    elif budget == "eco":
        budget_query = "(eco_weapon(X))"
    else:
        budget_query = f"(weapon_in_range(0, {budget}, X))"

    if weapon_type != "any":
        type_query = f"(weapon_type(X, {weapon_type}))"
    else:
        type_query = "true"

    if side == "terrorists":
        side_query = "(weapon_side(X, terrorists); weapon_side(X, both))"
    elif side == "counter_terrorists":
        side_query = "(weapon_side(X, counter_terrorists); weapon_side(X, both))"
    else:
        side_query = "true"

    query = f"{budget_query}, {type_query}, {side_query}"

    for result in prolog.query(query):
        recommendations.append(result["X"].decode('utf-8'))

    return recommendations