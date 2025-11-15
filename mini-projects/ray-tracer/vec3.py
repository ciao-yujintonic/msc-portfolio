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
    
    def __truediv__(self, t):
        return Vec3(self.x / t, self.y / t, self.z / t)

    # make 2 * v behave the same as v * 2 (Python calls __rmul__ for reversed operands)
    __rmul__ = __mul__

    def dot(self, other):
        return self.x * other.x + self.y * other.y + self.z * other.z

    def length(self):
        return math.sqrt(self.dot(self))

    def unit(self):
        return self * (1.0 / self.length())

    # Q : Why color into vector?
    # A : RGB is essentially caclulated as three numbers. These values must be operated such as addition, multiplication, scaling, and interpolation.
    # Q : But then why 0 - 1, not 0 - 255, the usual color range?
    # A : In graphics, color is actually the intensity of light. So, it is more natural to express color as a ratio (0 - 1) rather than absolute value (0 - 255).
    # And it's easier to do calculations with normalized values. So when we create an image as the last step, we convert these normalized values to the 0 - 255 range.
    def to_color(self):
        # Q: Why 255.999 instead 255?
        # A: Using 255 could occur rounding issues that might lead to incorrect color representation. So we use 255.999 to ensure proper rounding preventing off-by-one errors.
        r = int(255.999 * self.x)
        g = int(255.999 * self.y)
        b = int(255.999 * self.z)
        return (r, g, b)
