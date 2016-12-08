'use strict';

module.exports = function (app) {
  let controller = {};

  controller.getVaga = function (req, res) {
    console.log('Get vaga');
  }

  return controller;
};
