package org.beobma.classwar.util

import org.beobma.classwar.CLASSWAR
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import kotlin.math.acos
import kotlin.math.cbrt
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class Particle : Listener {

    companion object {
        fun spawnParticleRadius(location: Location, particle: Particle, radius: Double, second: Double) {
            val tick = (second * 20) / 5
            var ticksElapsed = 0
            CLASSWAR.instance.server.scheduler.runTaskTimer(CLASSWAR.instance, Runnable {
                if (ticksElapsed >= tick) {
                    return@Runnable
                }
                for (angle in 0..360 step 10) {
                    val radian = Math.toRadians(angle.toDouble())
                    val x = location.x + radius * cos(radian)
                    val y = location.y
                    val z = location.z + radius * sin(radian)
                    location.world.spawnParticle(particle, Location(location.world, x, y, z), 1, 0.0, 0.0, 0.0, 0.0)
                }
                for (innerRadius in 1 until radius.toInt()) {
                    for (angle in 0..360 step 10) {
                        val radian = Math.toRadians(angle.toDouble())
                        val x = location.x + innerRadius * cos(radian)
                        val y = location.y
                        val z = location.z + innerRadius * sin(radian)
                        location.world.spawnParticle(particle, Location(location.world, x, y, z), 1, 0.0, 0.0, 0.0, 0.0)
                    }
                }
                ticksElapsed++
            }, 0L, 5L)
        }


        fun spawnSphereParticles(center: Location, particle: Particle, radius: Double) {
            val horizontalParticleCount = 25
            val verticalParticleCount = 10
            val horizontalIncrement = (2 * Math.PI) / horizontalParticleCount
            val verticalIncrement = Math.PI / verticalParticleCount

            for (i in 0 until horizontalParticleCount) {
                val horizontalAngle = horizontalIncrement * i

                for (j in 0 until verticalParticleCount) {
                    val verticalAngle = verticalIncrement * j

                    val x = center.x + radius * sin(horizontalAngle) * sin(verticalAngle)
                    val y = center.y + radius * cos(verticalAngle)
                    val z = center.z + radius * cos(horizontalAngle) * sin(verticalAngle)

                    val particleLocation = Location(center.world, x, y, z)
                    center.world.spawnParticle(particle, particleLocation, 1, 0.0, 0.0, 0.0, 0.0)
                }
            }
        }

        /**
         * 플레이어가 바라보는 방향으로 부채꼴 모양의 파티클을 생성합니다.
         * @param player 플레이어
         * @param particle 생성할 파티클의 종류
         * @param radius 부채꼴의 반지름
         * @param particles 파티클의 수
         * @param spread 부채꼴의 각도(도)
         */
        fun spawnFanShapeParticles(player: Player, particle: Particle, radius: Double, particles: Int, spread: Double) {
            val world = player.world
            val location = player.eyeLocation
            val direction = location.direction.normalize()

            val angleIncrement = Math.toRadians(spread / particles)

            for (i in 0 until particles) {
                val angle = angleIncrement * i - Math.toRadians(spread / 2)
                val x = direction.x * cos(angle) - direction.z * sin(angle)
                val z = direction.x * sin(angle) + direction.z * cos(angle)

                val particleLocation = location.clone().add(x * radius, 0.0, z * radius)
                world.spawnParticle(particle, particleLocation, 1, 0.0, 0.0, 0.0, 0.0)
            }
        }

        /**
         * 구 형태 영역에 파티클 생성 함수
         * @param center 구의 중심이 될 위치
         * @param radius 구의 반지름
         * @param world 마인크래프트의 월드 객체
         * @param particle 생성할 파티클의 종류
         * @param particleCount 구 형태에 생성할 파티클의 총 개수
         */
        fun createParticlesInSphere(center: Location, radius: Double, world: World, particle: Particle, particleCount: Int, block: BlockData? = null) {
            repeat(particleCount) {
                // 구 내부의 무작위 점을 생성하기 위한 무작위 방향과 거리
                val phi = Random.nextDouble(0.0, 2 * Math.PI)
                val costheta = Random.nextDouble(-1.0, 1.0)
                val u = Random.nextDouble(0.0, 1.0)

                val theta = acos(costheta)
                val r = radius * cbrt(u) // 반지름에 대한 세제곱근을 사용하여 내부를 채움

                // 구면 좌표계에서의 위치 계산
                val x = r * sin(theta) * cos(phi)
                val y = r * sin(theta) * sin(phi)
                val z = r * cos(theta)

                val location = center.clone().add(x, y, z)
                if (block != null) {
                    world.spawnParticle(particle, location, 1, 0.0, 0.0, 0.0, 0.0, block)
                } else {
                    world.spawnParticle(particle, location, 1, 0.0, 0.0, 0.0, 0.0)
                }
            }
        }

        fun playRectangleBorderParticles(loc1: Location, loc2: Location, particle: Particle, world: World) {
            val minX = loc1.x.coerceAtMost(loc2.x) - 0.5
            val maxX = loc1.x.coerceAtLeast(loc2.x) + 0.5
            val minY = loc1.y.coerceAtMost(loc2.y) - 0.5
            val maxY = loc1.y.coerceAtLeast(loc2.y) + 0.5
            val minZ = loc1.z.coerceAtMost(loc2.z) - 0.5
            val maxZ = loc2.z.coerceAtLeast(loc1.z) + 0.5

            for (x in minX.toInt() until maxX.toInt()) {
                world.spawnParticle(particle, Location(world, x.toDouble(), minY, minZ), 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(particle, Location(world, x.toDouble(), minY, maxZ), 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(particle, Location(world, x.toDouble(), maxY, minZ), 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(particle, Location(world, x.toDouble(), maxY, maxZ), 1, 0.0, 0.0, 0.0, 0.0)
            }

            for (y in minY.toInt() until maxY.toInt()) {
                world.spawnParticle(particle, Location(world, minX, y.toDouble(), minZ), 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(particle, Location(world, minX, y.toDouble(), maxZ), 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(particle, Location(world, maxX, y.toDouble(), minZ), 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(particle, Location(world, maxX, y.toDouble(), maxZ), 1, 0.0, 0.0, 0.0, 0.0)
            }

            for (z in minZ.toInt() until maxZ.toInt()) {
                world.spawnParticle(particle, Location(world, minX, minY, z.toDouble()), 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(particle, Location(world, minX, maxY, z.toDouble()), 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(particle, Location(world, maxX, minY, z.toDouble()), 1, 0.0, 0.0, 0.0, 0.0)
                world.spawnParticle(particle, Location(world, maxX, maxY, z.toDouble()), 1, 0.0, 0.0, 0.0, 0.0)
            }
        }

        /**
         * 특정 블록의 모서리에 파티클을 재생합니다.
         * @param world 파티클을 재생할 월드.
         * @param blockCenter 블록의 중심 위치.
         * @param particle 재생할 파티클 종류.
         */
        fun playParticlesAtBlockCorners(world: World, blockCenter: Location, particle: Particle) {
            val offsets = listOf(-0.5, 0.5)

            for (x in offsets) {
                for (y in offsets) {
                    for (z in offsets) {
                        val corner = blockCenter.clone().add(x, y, z)
                        world.spawnParticle(particle, corner, 1, 0.0, 0.0, 0.0, 0.0)
                    }
                }
            }
        }
    }
}