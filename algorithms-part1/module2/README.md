# module 2

## Quick-Find

- Uses array `id[]`: same component → same id
- `find` is fast
- `union` is slow → must scan entire array → O(N)

---

## Quick-Union

- Uses `id[]` as parent pointers → forest (trees)
- Root represents component
- `union` connects roots → fast
- Bad case: trees can become tall → `find` becomes O(N)

---

## Weighted Quick-Union

- Always attach smaller tree under larger tree
- Keeps trees balanced → height ≤ log N
- `find` / `union`: O(log N)

---

## Path Compression

- During `find`, make nodes point closer to root
- Makes tree almost flat
- Very fast in practice → ~ O(1)

---

| Method                           | find      | union     | worst-case time |
| -------------------------------- | --------- | --------- | --------------- |
| Quick-Find                       | fast      | slow      | M N             |
| Quick-Union                      | slow      | fast      | M N             |
| Weighted Q.U.                    | fast      | fast      | N + M Log N     |
| Weighted Q.U. + Path Compression | very fast | very fast | N + M Lg\* N    |

> Weighted Quick-Union + Path Compression → **best practical performance**
