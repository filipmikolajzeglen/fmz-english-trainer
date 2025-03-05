package com.filipmikolajzeglen.fmzenglishtrainer.translation.idiom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IdiomRepository extends JpaRepository<Idiom, Long>
{
}