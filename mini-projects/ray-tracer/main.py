from PIL import Image
from vec3 import Vec3
from sphere import Sphere
from camera import Camera
from ray import Ray

def ray_color(ray, world):
    hit_anything = None
    # sometimes we get shadow acne due to floating point precision issue, so we ignore if any hit is smaller than t_min
    # otherwise, a black dot can be generated on the surface (=shadow acne)
    t_min = 0.001
    # we only care about the closest hit, so we can prevent checking hits farther than the closest one found so far
    t_max = 1e9

    for obj in world:
        hit = obj.hit(ray, t_min, t_max)
        if hit:
            # if hits something, update t_max to check closer hits only
            t_max = hit.t
            hit_anything = hit

    if hit_anything:
        N = hit_anything.normal
        # N.x, N.y, N.z ∈ [-1, +1] -> N.x+1, N.y+1, N.z+1 ∈ [0, 2] -> 0.5*(N.x+1), 0.5*(N.y+1), 0.5*(N.z+1) ∈ [0,1] => RGB
        # But this doesn't represent the sphere's real color
        return Vec3(0.5*(N.x + 1), 0.5*(N.y + 1), 0.5*(N.z + 1))

    # ray didn't hit anything, return the background color
    unit_dir = ray.direction.unit()
    # ray points upward -> t= 1.0, downward -> t=0.0
    t = 0.5 * (unit_dir.y + 1.0)
    # depending on t, Linear Interpolation (lerp) t ≈ 1 -> more blue, t ≈ 0 -> more white
    return (1.0 - t) * Vec3(1.0, 1.0, 1.0) + t * Vec3(0.5, 0.7, 1.0)

def main():
    # keep the ratio of the image as 16 : 9
    # For the fast rendering, we start from the small size. This is easier to change the size of the image later.
    aspect_ratio = 16.0 / 9.0
    # The number of pixels which devides viewport
    image_width = 400
    image_height = int(image_width / aspect_ratio)

    # world is a list which includes every existed object in the scene.
    # And later, we will check the intersection between the ray and every object in the world.
    world = [
        # A sphere (center = (0,0,-1) / radius = 0.5 / color = (0.8,0.3,0.3))
        Sphere(Vec3(0, 0, -1), 0.5, Vec3(0.8, 0.3, 0.3)),
        # A sphere as a tricked-ground with the big radius (the top of the sphere is y = -0.5)
        Sphere(Vec3(0, -100.5, -1), 100, Vec3(0.8, 0.8, 0))
    ]

    cam = Camera(aspect_ratio)

    # create a new image with pixels with (0,0,0) black
    image = Image.new("RGB", (image_width, image_height))

    # rendering
    # for each pixel, shoot a ray through the pixel and get the color
    for j in range(image_height):
        for i in range(image_width):
            # u and v are the scale factors to move horizontal and vertical direction on the viewport
            # using these ratios, we get exact 3D position and shot ray
            # u : 0 -> left , 1 -> right
            u = i / (image_width - 1)
            # since j starts from top to bottom, we need to flip it, because on the viewport v:0 -> bottom , 1 -> top
            v = (image_height - j - 1) / (image_height - 1)
            ray = cam.get_ray(u, v)

            color = ray_color(ray, world).to_color()
            image.putpixel((i, j), color)

    image.save("image_focal3.png")
    print("렌더링 완료 → image.png 저장됨!")

if __name__ == "__main__":
    main()
