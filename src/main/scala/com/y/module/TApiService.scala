package com.y.module

import com.y.NesterProcessor


trait TApiService {
  def configService: String
  def fetcher: FetcherProcessor
  def transformer: TransformerProcessor
  def nester: NesterProcessor
  def repository: String

  def process(): Map[String, Any] = {
    configService
    fetcher
    transformer
    nester.go
    Map()
  }
}

trait FetcherProcessor
class FetcherImpl extends FetcherProcessor

trait TransformerProcessor
class TransformerTraining extends TransformerProcessor
class TransformerGershwin extends TransformerProcessor

class TrainingService extends TApiService {
  override def fetcher: FetcherProcessor = new FetcherImpl
  override def transformer: TransformerProcessor = new TransformerTraining
  override def nester: NesterProcessor = new NesterProcessor
  override def configService: String = "thisOne"
  override def repository: String = "pgRepository"
}

class GershwinService extends TrainingService {
  override def transformer: TransformerProcessor = new TransformerGershwin
  override def process(): Map[String, Any] = {
    fetcher
    transformer
    Map()
  }
}

//controllers are autonomous
//extends TApiService
