package com.criteo.slab.app

import com.criteo.slab.core.{Box, Layout}
import com.criteo.slab.utils.Jsonable
import org.json4s.JsonAST.{JArray, JString}
import org.json4s.{CustomSerializer, Serializer}

/** Represents the configuration of a board, to be used by the web app */
private[slab] case class BoardConfig(
                    title: String,
                    layout: Layout,
                    links: Seq[(Box, Box)] = Seq.empty
                   )

object BoardConfig {
  implicit object ToJSON extends Jsonable[BoardConfig] {
    override val serializers: Seq[Serializer[_]] =
      implicitly[Jsonable[Box]].serializers ++
        implicitly[Jsonable[Layout]].serializers :+
        LinkSer

    object LinkSer extends CustomSerializer[Box Tuple2 Box](_ => ( {
      case _ => throw new NotImplementedError("Not deserializable")
    }, {
      case (Box(title1, _, _, _, _), Box(title2, _, _, _, _)) => JArray(List(JString(title1), JString(title2)))
    }
    ))

  }
}
