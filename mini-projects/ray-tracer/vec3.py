import math

class Vec3:
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z

    def __add__(self, other):
        return Vec3(self.x + other.x, self.y + other.y, self.z + other.z)

    def __sub__(self, other):
        return Vec3(self.x - other.x, self.y - other.y, self.z - other.z)

    def __mul__(self, t):
        return Vec3(self.x * t, self.y * t, self.z * t)

    __rmul__ = __mul__

    def dot(self, other):
        return self.x * other.x + self.y * other.y + self.z * other.z

    def length(self):
        return math.sqrt(self.dot(self))

    def unit(self):
        return self * (1.0 / self.length())

    def to_color(self):
        r = int(255.999 * self.x)
        g = int(255.999 * self.y)
        b = int(255.999 * self.z)
        return (r, g, b)
