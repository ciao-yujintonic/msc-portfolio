from PIL import Image
from vec3 import Vec3
from sphere import Sphere
from camera import Camera
from ray import Ray

def ray_color(ray, world):
    hit_anything = None
    t_min = 0.001
    t_max = 1e9

    for obj in world:
        hit = obj.hit(ray, t_min, t_max)
        if hit:
            t_max = hit.t
            hit_anything = hit

    if hit_anything:
        # 단순한 Lambert 조명 없이 Normal 기반 색상
        N = hit_anything.normal
        return Vec3(0.5*(N.x + 1), 0.5*(N.y + 1), 0.5*(N.z + 1))

    # 배경색
    unit_dir = ray.direction.unit()
    t = 0.5 * (unit_dir.y + 1.0)
    return (1.0 - t) * Vec3(1.0, 1.0, 1.0) + t * Vec3(0.5, 0.7, 1.0)

def main():
    # 이미지 크기
    aspect_ratio = 16.0 / 9.0
    image_width = 400
    image_height = int(image_width / aspect_ratio)

    # 월드 구성
    world = [
        Sphere(Vec3(0, 0, -1), 0.5, Vec3(0.8, 0.3, 0.3)),
        Sphere(Vec3(0, -100.5, -1), 100, Vec3(0.8, 0.8, 0))
    ]

    cam = Camera()

    image = Image.new("RGB", (image_width, image_height))

    # 렌더링
    for j in range(image_height):
        for i in range(image_width):
            u = i / (image_width - 1)
            v = (image_height - j - 1) / (image_height - 1)
            ray = cam.get_ray(u, v)

            color = ray_color(ray, world).to_color()
            image.putpixel((i, j), color)

    image.save("image.png")
    print("렌더링 완료 → image.png 저장됨!")

if __name__ == "__main__":
    main()
