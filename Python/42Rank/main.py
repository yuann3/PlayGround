def calculate_s(C, i, n):
    return (C / i) * ((1 + i)**n - 1)

S = calculate_s(4000, 0.069, 5)


print(f"S is {S}")
